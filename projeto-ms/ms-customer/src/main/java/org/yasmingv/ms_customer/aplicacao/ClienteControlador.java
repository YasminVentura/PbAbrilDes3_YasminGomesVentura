package org.yasmingv.ms_customer.aplicacao;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_customer.aplicacao.dto.ClienteDTO;

@Tag(name = "Cliente", description = "Endpoints relacionados ao cliente")
@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class ClienteControlador {

    private final ClienteServico servico;

    @PostMapping
    @Operation(summary = "Criar cliente",
            description = "Recurso para criar um novo cliente através de um JSON",
            responses = @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))))
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO svClienteDTO = servico.salvar(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(svClienteDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID",
            description = "Recurso para buscar um cliente por ID",
            responses = @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class))))
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable Long id) {
        ClienteDTO clienteDTO = servico.buscarPorId(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar dados por ID",
            description = "Recurso para atualizar dados de um cliente por ID",
            responses = @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteDTO.class)))    )
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO atualizarClienteDTO = servico.atualizar(id, clienteDTO);
        return ResponseEntity.ok(atualizarClienteDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cliente por ID",
            description = "Recurso para deletar um cliente por ID",
            responses = @ApiResponse(responseCode = "204"))
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
