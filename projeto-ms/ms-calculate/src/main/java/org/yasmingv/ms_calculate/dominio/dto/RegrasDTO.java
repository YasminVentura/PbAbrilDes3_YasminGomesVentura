package org.yasmingv.ms_calculate.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yasmingv.ms_calculate.dominio.Regras;

@Data
@NoArgsConstructor
public class RegrasDTO {

    private Long id;

    @NotBlank
    private String categoria;

    @NotNull
    private Integer pariedade;

    public RegrasDTO(Regras regras){
        this.id = regras.getId();
        this.categoria = regras.getCategoria();
        this.pariedade = regras.getPariedade();
    }

    public Regras paraRegras(){
        return new Regras(categoria, pariedade);
    }
}
