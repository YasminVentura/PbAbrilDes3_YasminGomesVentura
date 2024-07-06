package org.yasmingv.ms_payment.aplicacao;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_payment.aplicacao.dto.PagamentoDTO;

import java.util.UUID;

@Tag(name = "Pagamentos", description = "Endpoints relacionados ao pagamento")
@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PagamentoControlador {

    private final PagamentoServico servico;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO pagamento = servico.salvar(pagamentoDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> buscarPagamento(@PathVariable UUID id) {
        PagamentoDTO pagamentoDTO = servico.buscarPagamentoPorId(id);
        return  ResponseEntity.ok(pagamentoDTO);
    }

    //GET - /v1/payments/user/:userId
}
