package org.yasmingv.ms_payment.aplicacao;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yasmingv.ms_payment.aplicacao.dto.PagamentoDTO;
import org.yasmingv.ms_payment.aplicacao.dto.Pontos;
import org.yasmingv.ms_payment.config.RabbitMQConfig;
import org.yasmingv.ms_payment.dominio.Pagamento;
import org.yasmingv.ms_payment.excecoes.ex.ClienteNaoEncontradoExcecao;
import org.yasmingv.ms_payment.infra.PagamentoRepositorio;
import org.yasmingv.ms_payment.infra.feign.MsCalculate;
import org.yasmingv.ms_payment.infra.feign.MsCustomer;
import org.yasmingv.ms_payment.infra.feign.dto.CalculadorDTO;
import org.yasmingv.ms_payment.infra.feign.dto.CalculadorResposta;
import org.yasmingv.ms_payment.infra.feign.dto.ClienteDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PagamentoServico {

    private final PagamentoRepositorio repositorio;

    private final MsCustomer msClienteFeign;

    private final MsCalculate msCalculateFeign;

    private final RabbitTemplate rabbitTemplate;

    @Transactional
    public PagamentoDTO salvar(PagamentoDTO pagamentoDTO) {
        ResponseEntity<ClienteDTO> clienteResponse = msClienteFeign.buscarCliente(pagamentoDTO.getClienteId());
        if (!clienteResponse.getStatusCode().is2xxSuccessful() || clienteResponse.getBody() == null) {
            throw new ClienteNaoEncontradoExcecao("Cliente não encontrado");
        }

        Pagamento pagamento = pagamentoDTO.paraPagamento();
        pagamento.setDataCriacao(LocalDateTime.now());
        pagamento = repositorio.save(pagamento);

        CalculadorDTO calculadorDTO = new CalculadorDTO(pagamento.getCategoriaId(), pagamento.getTotal());
        ResponseEntity<CalculadorResposta> calculadorResponse = msCalculateFeign.calcularPontos(calculadorDTO);
        if (!calculadorResponse.getStatusCode().is2xxSuccessful() || calculadorResponse.getBody() == null) {
            throw new IllegalArgumentException("Erro ao calcular pontos");
        }

        Integer pontos = calculadorResponse.getBody().getTotal().intValue();
        Pontos pontosMensagem = new Pontos(pagamento.getClienteId(), pontos);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, pontosMensagem);

        return new PagamentoDTO(pagamento);
    }
}
