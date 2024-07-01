package org.yasmingv.ms_calculate.aplicacao;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yasmingv.ms_calculate.aplicacao.dto.RegrasDTO;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/rules")
public class RegrasControlador {

    private final RegrasServico servico;

    @PostMapping
    public ResponseEntity<RegrasDTO> criarRegra(@Valid @RequestBody RegrasDTO regrasDTO) {
        RegrasDTO regras = servico.salvar(regrasDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(regras);
    }

    //GET - /v1/rules


    //PUT - /v1/rules/:id


    //DELETE - /v1/rules/:id

}
