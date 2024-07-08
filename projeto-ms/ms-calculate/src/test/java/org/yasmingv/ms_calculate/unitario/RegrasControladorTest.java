package org.yasmingv.ms_calculate.unitario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.yasmingv.ms_calculate.controlador.RegrasControlador;
import org.yasmingv.ms_calculate.servico.RegrasServico;
import org.yasmingv.ms_calculate.dominio.dto.RegrasDTO;

import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegrasControladorTest {

    @InjectMocks
    private RegrasControlador regrasControlador;

    @Mock
    private RegrasServico regrasServico;

    private RegrasDTO criarRegras() {
        RegrasDTO regras = new RegrasDTO();
        regras.setCategoria("Electronicos");
        regras.setPariedade(10);
        return regras;
    }

    @Test
    void criarRegra_retornarSTTS201() {
        RegrasDTO regrasDTO = criarRegras();
        RegrasDTO regrasSalvasDTO = criarRegras();
        regrasSalvasDTO.setId(1L);

        when(regrasServico.salvar(regrasDTO)).thenReturn(regrasSalvasDTO);

        ResponseEntity<RegrasDTO> responseEntity = regrasControlador.criarRegra(regrasDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        RegrasDTO responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertEquals(1L, responseBody.getId());
        assertEquals(regrasDTO.getCategoria(), responseBody.getCategoria());
        assertEquals(regrasDTO.getPariedade(), responseBody.getPariedade());
    }

    @Test
    void listarRegras_retornarSTTS200() {
        List<RegrasDTO> regrasDTOList = Arrays.asList(criarRegras(), criarRegras());
        when(regrasServico.buscarTodasRegras()).thenReturn(regrasDTOList);

        ResponseEntity<List<RegrasDTO>> responseEntity = regrasControlador.listarRegras();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        List<RegrasDTO> responseBody = responseEntity.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.size());
    }

    @Test
    void atualizarRegra_retornarSTTS200() {
        Long id = 1L;
        RegrasDTO regrasDTO = criarRegras();
        RegrasDTO regrasAtualizadasDTO = criarRegras();
        regrasAtualizadasDTO.setId(id);
        regrasAtualizadasDTO.setCategoria("Jogos");

        when(regrasServico.atualizar(any(Long.class), any(RegrasDTO.class))).thenReturn(regrasAtualizadasDTO);

        ResponseEntity<RegrasDTO> responseEntity = regrasControlador.atualizarRegra(id, regrasDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        RegrasDTO responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertEquals(id, responseBody.getId());
        assertEquals("Jogos", responseBody.getCategoria());
        assertEquals(regrasDTO.getPariedade(), responseBody.getPariedade());
    }

    @Test
    void excluirRegra_retornarSTTS204() {
        Long id = 1L;

        doNothing().when(regrasServico).excluir(id);

        ResponseEntity<Void> responseEntity = regrasControlador.excluirRegra(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

}
