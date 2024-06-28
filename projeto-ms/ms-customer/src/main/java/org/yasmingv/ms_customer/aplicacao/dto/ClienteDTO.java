package org.yasmingv.ms_customer.aplicacao.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;
//import org.hibernate.validator.constraints.br.CPF;
import org.yasmingv.ms_customer.dominio.Cliente;

import java.time.LocalDate;

@Data
public class ClienteDTO {

    private Long id;

    //@CPF
    private String cpf;

    @NotBlank
    @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres")
    private String nome;

    @NotBlank
    @Pattern(regexp = "Masculino|Feminino", message = "Apenas Masculino ou Feminino")
    private String genero;

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate aniversario;

    @NotBlank
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private String url_foto;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.cpf = cliente.getCpf();
        this.nome = cliente.getNome();
        this.genero = cliente.getGenero();
        this.aniversario = cliente.getAniversario();
        this.email = cliente.getEmail();
        this.url_foto = cliente.getUrl_foto();
    }

    public Cliente paraCliente(){
        return new Cliente(cpf, nome, genero, aniversario, email, url_foto);
    }

}
