package org.yasmingv.ms_customer.infra.mqueue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.yasmingv.ms_customer.dominio.Cliente;
import org.yasmingv.ms_customer.excecoes.ex.ClienteNaoEncontradoExcecao;
import org.yasmingv.ms_customer.infra.repositorio.ClienteRepositorio;

@Slf4j
@Component
@RequiredArgsConstructor
public class PontosMensagem {

    private final ClienteRepositorio repositorio;

    @RabbitListener(queues = "${mq.queues.pontos-mensagem}")
    public void recebePontosMensagem(@Payload String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Cliente pontosMensagem = mapper.readValue(payload, Cliente.class);

            Long clienteId = pontosMensagem.getId();
            Integer pontos = pontosMensagem.getPontos();

            Cliente cliente = repositorio.findById(clienteId)
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            cliente.setPontos(cliente.getPontos() + pontos);
            repositorio.save(cliente);

            log.info("Pontos atualizados para o cliente com ID {}: {} pontos adicionados", clienteId, pontos);
        } catch (Exception e) {
            log.error("Erro ao processar mensagem de pontos: {}", e.getMessage());
        }
    }
}
