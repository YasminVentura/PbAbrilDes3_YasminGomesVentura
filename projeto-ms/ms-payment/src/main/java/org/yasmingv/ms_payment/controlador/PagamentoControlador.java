package org.yasmingv.ms_payment.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_payment.servico.PagamentoServico;
import org.yasmingv.ms_payment.dominio.dto.PagamentoDTO;
import org.yasmingv.ms_payment.excecoes.MensagemErro;
import java.util.List;
import java.util.UUID;

@Tag(name = "Pagamentos", description = "Endpoints relacionados ao pagamento")
@RestController
@RequestMapping("/v1/payments")
@RequiredArgsConstructor
public class PagamentoControlador {

    private final PagamentoServico servico;

    @PostMapping
    @Operation(
            summary = "Criar Pagamento",
            description = "Recurso para criar um novo pagamento através de um JSON",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pagamento realizado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PagamentoDTO.class))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErro.class)))
            }
    )
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO pagamento = servico.salvar(pagamentoDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(pagamento);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID",
            description = "Recurso para buscar um pagamento por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento encontrado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PagamentoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErro.class)))
            }
    )
    public ResponseEntity<PagamentoDTO> buscarPagamento(@PathVariable UUID id) {
        PagamentoDTO pagamentoDTO = servico.buscarPagamentoPorId(id);
        return  ResponseEntity.ok(pagamentoDTO);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Buscar cliente por ID",
            description = "Recurso para buscar um cliente por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PagamentoDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MensagemErro.class)))
            }
    )
    public ResponseEntity<List<PagamentoDTO>> buscarCliente(@PathVariable Long id) {
        List<PagamentoDTO> pagamentos = servico.buscarClientePorId(id);
        return ResponseEntity.ok(pagamentos);
    }
}
