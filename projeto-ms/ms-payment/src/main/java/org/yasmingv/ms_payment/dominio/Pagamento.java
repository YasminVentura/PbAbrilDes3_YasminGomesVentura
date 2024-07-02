package org.yasmingv.ms_payment.dominio;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Long clienteId;

    private Long categoriaId;

    private Double total;

    private LocalDateTime dataCriacao;

    public Pagamento(Long clienteId, Long categoriaId, Double total) {
        this.clienteId = clienteId;
        this.categoriaId = categoriaId;
        this.total = total;
    }

}
