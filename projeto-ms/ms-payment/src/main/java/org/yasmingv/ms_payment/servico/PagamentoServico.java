package org.yasmingv.ms_payment.servico;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yasmingv.ms_payment.dominio.dto.PagamentoDTO;
import org.yasmingv.ms_payment.dominio.dto.Pontos;
import org.yasmingv.ms_payment.dominio.Pagamento;
import org.yasmingv.ms_payment.excecoes.ex.IdNaoEncontradoExcecao;
import org.yasmingv.ms_payment.conectividade.mqueue.PontosMensagem;
import org.yasmingv.ms_payment.repositorio.PagamentoRepositorio;
import org.yasmingv.ms_payment.conectividade.feign.MsCalculate;
import org.yasmingv.ms_payment.conectividade.feign.MsCustomer;
import org.yasmingv.ms_payment.conectividade.feign.dto.CalculadorDTO;
import org.yasmingv.ms_payment.conectividade.feign.dto.CalculadorResposta;
import org.yasmingv.ms_payment.conectividade.feign.dto.ClienteDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagamentoServico {

    private final PagamentoRepositorio repositorio;

    private final MsCustomer msClienteFeign;

    private final MsCalculate msCalculateFeign;

    private final PontosMensagem pontosMensagem;

    @Transactional
    public PagamentoDTO salvar(PagamentoDTO pagamentoDTO) {
        ResponseEntity<ClienteDTO> clienteResponse = msClienteFeign.buscarCliente(pagamentoDTO.getClienteId());
        if (!clienteResponse.getStatusCode().is2xxSuccessful() || clienteResponse.getBody() == null) {
            throw new IdNaoEncontradoExcecao("Cliente não encontrado");
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

        try {
            this.pontosMensagem.enviarPontos(pontosMensagem);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao enviar mensagem de pontos para o RabbitMQ", e);
        }

        return new PagamentoDTO(pagamento);
    }

    public PagamentoDTO buscarPagamentoPorId(UUID id) {
        Pagamento pagamento = repositorio.findById(id).
                orElseThrow(() -> new IdNaoEncontradoExcecao("Pagamento não encontrado"));

        return new PagamentoDTO(pagamento);
    }

    public List<PagamentoDTO> buscarClientePorId(Long clienteId) {
        List<Pagamento> pagamentos = repositorio.findByClienteId(clienteId);
        if (pagamentos.isEmpty()) {
            throw new IdNaoEncontradoExcecao("Cliente não encontrado");
        }

        return pagamentos.stream()
                .map(PagamentoDTO::new)
                .collect(Collectors.toList());
    }
}
