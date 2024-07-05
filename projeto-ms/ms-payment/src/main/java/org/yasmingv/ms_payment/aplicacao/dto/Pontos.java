package org.yasmingv.ms_payment.aplicacao.dto;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
public class Pontos implements Serializable {
    private Long Id;
    private Integer pontos;

    public Pontos(Long Id, Integer pontos) {
        this.Id = Id;
        this.pontos = pontos;
    }
}
