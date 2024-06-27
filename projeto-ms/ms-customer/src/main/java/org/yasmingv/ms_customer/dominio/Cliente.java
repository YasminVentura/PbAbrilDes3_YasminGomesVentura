package org.yasmingv.ms_customer.dominio;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "NOME", length = 100, nullable = false)
    private String nome;

    @Column(name = "GENERO", length = 9, nullable = false)
    private String genero;

    @Column(name = "ANIVERSARIO", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate aniversario;

    @Column(name = "EMAIL", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "PONTOS")
    private Integer pontos = 0;

    @Column(name = "URL_FOTO", nullable = false)
    private String url_foto;

    public Cliente(String cpf, String nome, String genero, LocalDate aniversario, String email, String url_foto) {
        this.cpf = cpf;
        this.nome = nome;
        this.genero = genero;
        this.aniversario = aniversario;
        this.email = email;
        this.url_foto = url_foto;
    }
}
