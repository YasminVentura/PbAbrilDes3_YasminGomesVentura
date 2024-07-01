package org.yasmingv.ms_calculate.aplicacao.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class CalculadorDTO {

    @NotNull
    private Long categoriaID;

    @NotNull
    private Double valor;
}
