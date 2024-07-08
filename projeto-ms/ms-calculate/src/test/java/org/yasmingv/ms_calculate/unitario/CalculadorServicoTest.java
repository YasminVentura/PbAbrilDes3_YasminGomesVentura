package org.yasmingv.ms_calculate.unitario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yasmingv.ms_calculate.servico.CalculadorServico;
import org.yasmingv.ms_calculate.dominio.dto.CalculadorDTO;
import org.yasmingv.ms_calculate.dominio.Regras;
import org.yasmingv.ms_calculate.repositorio.RegrasRepositorio;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculadorServicoTest {

    @Mock
    private RegrasRepositorio repositorioMock;

    @InjectMocks
    private CalculadorServico calculadorServico;

    private CalculadorDTO criarCalculadorDTO() {
        CalculadorDTO calculadorDTO = new CalculadorDTO();
        calculadorDTO.setCategoriaID(1L);
        calculadorDTO.setValor(100.0);
        return calculadorDTO;
    }

    private Regras criarRegra() {
        Regras regra = new Regras();
        regra.setId(1L);
        regra.setCategoria("Eletrônicos");
        regra.setPariedade(20);
        return regra;
    }

    @Test
    void testCalcularPontos() {
        CalculadorDTO calculadorDTO = criarCalculadorDTO();
        Regras regra = criarRegra();

        when(repositorioMock.findById(calculadorDTO.getCategoriaID())).thenReturn(Optional.of(regra));

        Double pontosCalculados = calculadorServico.calcularPontos(calculadorDTO);

        assertNotNull(pontosCalculados);
        assertEquals(2000, pontosCalculados);
    }
}

