package org.yasmingv.ms_payment.dominio.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yasmingv.ms_payment.dominio.Pagamento;

import java.util.UUID;


@Data
@NoArgsConstructor
public class PagamentoDTO {

    private UUID id;

    @NotNull
    private Long clienteId;
    @NotNull
    private Long categoriaId;
    @NotNull
    private Double total;

    public Pagamento paraPagamento() {
        return new Pagamento(clienteId, categoriaId, total);
    }

    public PagamentoDTO(Pagamento pagamento) {
        this.clienteId = pagamento.getClienteId();
        this.categoriaId = pagamento.getCategoriaId();
        this.total = pagamento.getTotal();
    }
}
