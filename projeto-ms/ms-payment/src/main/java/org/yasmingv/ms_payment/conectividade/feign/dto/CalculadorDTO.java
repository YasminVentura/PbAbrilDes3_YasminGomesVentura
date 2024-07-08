package org.yasmingv.ms_payment.conectividade.feign.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class CalculadorDTO {
    @NotNull
    private Long categoriaID;

    @NotNull
    private Double valor;

    public CalculadorDTO(Long categoriaId, Double total) {
        this.categoriaID = categoriaId;
        this.valor = total;
    }
}
