package org.yasmingv.ms_calculate.aplicacao;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.yasmingv.ms_calculate.aplicacao.dto.CalculadorDTO;
import org.yasmingv.ms_calculate.aplicacao.dto.CalculadorResposta;

@Tag(name = "Calculador", description = "Endpoints relacionado ao calculo dos pontos")
@RestController
@AllArgsConstructor
@RequestMapping("/v1/calculate")
public class CalculadorControlador {

    private final CalculadorServico servico;

    @PostMapping
    @Operation(summary = "Calcular pontos",
            description = "Recurso para calcular o total de pontos que irá ser acumulado em uma compra",
            responses = @ApiResponse(responseCode = "200", description = "Pontos calculados com sucesso.",
                    content = @Content(schema = @Schema(implementation = CalculadorResposta.class))))
    public ResponseEntity<CalculadorResposta> calcularPontos(@RequestBody CalculadorDTO calculadorDTO) {
        Double pontosTotais = servico.calcularPontos(calculadorDTO);
        CalculadorResposta resposta = new CalculadorResposta(pontosTotais);
        return ResponseEntity.ok(resposta);
    }
}
