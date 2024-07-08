package org.yasmingv.ms_calculate.servico;

import lombok.AllArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.yasmingv.ms_calculate.dominio.dto.CalculadorDTO;
import org.yasmingv.ms_calculate.dominio.Regras;
import org.yasmingv.ms_calculate.repositorio.RegrasRepositorio;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CalculadorServico {

    private final RegrasRepositorio repositorio;

    public Double calcularPontos(CalculadorDTO calculadorDTO) {
        try {
            Long categoriaId = calculadorDTO.getCategoriaID();
            Double valor = calculadorDTO.getValor();

            Optional<Regras> regras = repositorio.findById(categoriaId);
            Integer paridade = regras.map(Regras::getPariedade).orElse(1);

            return valor * paridade;
        } catch (InvalidDataAccessApiUsageException | NullPointerException e) {
            throw new IllegalArgumentException("Categoria ID ou valor não pode ser nulo", e);
        }
    }

}
