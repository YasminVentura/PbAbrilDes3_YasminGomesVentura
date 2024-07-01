package org.yasmingv.ms_calculate.aplicacao;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.yasmingv.ms_calculate.aplicacao.dto.CalculadorDTO;
import org.yasmingv.ms_calculate.dominio.Regras;
import org.yasmingv.ms_calculate.infra.RegrasRepositorio;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CalculadorServico {

    private final RegrasRepositorio repositorio;

    public Double calcularPontos(CalculadorDTO calculadorDTO) {
        Long categoriaId = calculadorDTO.getCategoriaID();
        Double valor = calculadorDTO.getValor();

        Optional<Regras> regras = repositorio.findById(categoriaId);
        Double pariedade = regras.map(Regras::getPariedade)
                .map(Double::valueOf)
                .orElse(1.0);

        return valor * pariedade;
    }
}
