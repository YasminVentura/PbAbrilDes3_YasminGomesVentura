package org.yasmingv.ms_calculate.dominio;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "regras")
public class Regras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CATEGORIA", nullable = false)
    private String categoria;

    @Column(name = "PARIEDADE", nullable = false)
    private String pariedade;

    public Regras(String categoria, String pariedade) {
        this.categoria = categoria;
        this.pariedade = pariedade;
    }
}
