package org.yasmingv.ms_payment.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.yasmingv.ms_payment.aplicacao.dto.Pontos;

@Component
@RequiredArgsConstructor
public class PontosMensagem {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queuePontosMensagem;

    public void enviarPontos(Pontos pontos) throws JsonProcessingException {
        var json = convertIntoJson(pontos);
        rabbitTemplate.convertAndSend(queuePontosMensagem.getName(), json);
    }

    private String convertIntoJson(Pontos pontos) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(pontos);
    }
}

