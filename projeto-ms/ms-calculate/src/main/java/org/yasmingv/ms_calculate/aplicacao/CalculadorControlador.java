package org.yasmingv.ms_calculate.aplicacao;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.yasmingv.ms_calculate.aplicacao.dto.CalculadorDTO;
import org.yasmingv.ms_calculate.aplicacao.dto.CalculadorResposta;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/calculate")
public class CalculadorControlador {

    private final CalculadorServico servico;

    @PostMapping
    public ResponseEntity<CalculadorResposta> calcularPontos(@RequestBody CalculadorDTO calculadorDTO) {
        Double pontosTotais = servico.calcularPontos(calculadorDTO);
        CalculadorResposta resposta = new CalculadorResposta(pontosTotais);
        return ResponseEntity.ok(resposta);
    }
}
