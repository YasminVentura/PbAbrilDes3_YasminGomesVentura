package org.yasmingv.ms_calculate.unitario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.yasmingv.ms_calculate.controlador.CalculadorControlador;
import org.yasmingv.ms_calculate.servico.CalculadorServico;
import org.yasmingv.ms_calculate.dominio.dto.CalculadorDTO;
import org.yasmingv.ms_calculate.dominio.dto.CalculadorResposta;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculadorControladorTest {

    @InjectMocks
    private CalculadorControlador calculadorControlador;

    @Mock
    private CalculadorServico calculadorServico;

    private CalculadorDTO criarCalculadorDTO() {
        CalculadorDTO calculadorDTO = new CalculadorDTO();
        calculadorDTO.setCategoriaID(1L);
        calculadorDTO.setValor(100.0);
        return calculadorDTO;
    }

    @Test
    void testCalcularPontos() {
        CalculadorDTO calculadorDTO = criarCalculadorDTO();
        Double pontosCalculados = 2000.0;
        CalculadorResposta respostaEsperada = new CalculadorResposta(pontosCalculados);

        when(calculadorServico.calcularPontos(calculadorDTO)).thenReturn(pontosCalculados);

        ResponseEntity<CalculadorResposta> responseEntity = calculadorControlador.calcularPontos(calculadorDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        CalculadorResposta responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(respostaEsperada.getTotal(), responseBody.getTotal());
    }
}
