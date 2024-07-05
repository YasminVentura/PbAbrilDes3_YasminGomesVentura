package org.yasmingv.ms_payment.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${mq.queues.pontos-mensagem}")
    private String pontosMensagem;

    @Bean
    public Queue queuePontos_mensagem(){
        return new Queue( pontosMensagem , true);
    }
}
