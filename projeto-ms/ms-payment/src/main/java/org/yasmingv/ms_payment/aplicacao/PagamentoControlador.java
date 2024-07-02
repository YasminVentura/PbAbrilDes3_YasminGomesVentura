package org.yasmingv.ms_payment.aplicacao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasmingv.ms_payment.aplicacao.dto.PagamentoDTO;

@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PagamentoControlador {

    private final PaymentService servico;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO pagamento = servico.salvar(pagamentoDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }
}
