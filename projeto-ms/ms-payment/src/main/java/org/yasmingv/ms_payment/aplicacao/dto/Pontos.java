package org.yasmingv.ms_payment.aplicacao.dto;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
public class Pontos implements Serializable {
    private Long clienteId;
    private Integer pontos;

    public Pontos(Long clienteId, Integer pontos) {
        this.clienteId = clienteId;
        this.pontos = pontos;
    }
}
