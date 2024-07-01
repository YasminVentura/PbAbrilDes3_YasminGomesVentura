package org.yasmingv.ms_calculate.aplicacao;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_calculate.aplicacao.dto.RegrasDTO;

import java.util.List;

@Tag(name = "Regras", description = "Endpoints relacionados as regras")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/rules")
public class RegrasControlador {

    private final RegrasServico servico;

    @PostMapping
    @Operation(summary = "Criar regra",
            description = "Recurso para criar uma nova regra através de um JSON",
            responses = @ApiResponse(responseCode = "201", description = "Regra criada com sucesso.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegrasDTO.class))))
    public ResponseEntity<RegrasDTO> criarRegra(@Valid @RequestBody RegrasDTO regrasDTO) {
        RegrasDTO regras = servico.salvar(regrasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regras);
    }


    @GetMapping
    @Operation(summary = "Buscar todas as regras",
            description = "Recurso para buscar todas as regras cadastradas no sistema")
    public ResponseEntity<List<RegrasDTO>> listarRegras() {
         List<RegrasDTO> regras = servico.buscarTodasRegras();
        return ResponseEntity.ok(regras);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Atualizar regra",
            description = "Recurso para atualizar uma regra existente através de um JSON",
            responses = @ApiResponse(responseCode = "200", description = "Regra atualizada com sucesso.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegrasDTO.class)))
            )
    public ResponseEntity<RegrasDTO> atualizarRegra(@PathVariable Long id, @Valid @RequestBody RegrasDTO regrasDTO) {
        RegrasDTO regras = servico.atualizar(id, regrasDTO);
        return ResponseEntity.ok(regras);
    }

    //DELETE - /v1/rules/:id

}
