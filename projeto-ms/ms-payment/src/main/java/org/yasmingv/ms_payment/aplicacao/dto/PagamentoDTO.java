package org.yasmingv.ms_payment.aplicacao.dto;

import lombok.Data;
import org.yasmingv.ms_payment.dominio.Pagamento;

@Data
public class PagamentoDTO {

    private Long clienteId;
    private Long categoriaId;
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
