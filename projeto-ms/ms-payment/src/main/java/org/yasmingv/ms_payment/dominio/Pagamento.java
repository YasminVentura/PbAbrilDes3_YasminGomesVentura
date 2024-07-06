package org.yasmingv.ms_payment.dominio;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    @Column(name = "CLIENTE_ID")
    private Long clienteId;

    @Column(name = "CATEGORIA_ID")
    private Long categoriaId;

    @Column(name = "TOTAL")
    private Double total;

    @Column(name = "DATA_PAGAMENTO")
    private LocalDateTime dataCriacao;

    public Pagamento(Long clienteId, Long categoriaId, Double total) {
        this.clienteId = clienteId;
        this.categoriaId = categoriaId;
        this.total = total;
    }

}
