package org.yasmingv.ms_calculate.unitario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.yasmingv.ms_calculate.aplicacao.RegrasServico;
import org.yasmingv.ms_calculate.aplicacao.dto.RegrasDTO;
import org.yasmingv.ms_calculate.dominio.Regras;
import org.yasmingv.ms_calculate.excecoes.ex.RegraNaoEncontradoExcecao;
import org.yasmingv.ms_calculate.infra.RegrasRepositorio;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
public class RegrasServicoTest {

    @Mock
    private RegrasRepositorio repositorioMock;

    @InjectMocks
    private RegrasServico servico;

    private RegrasDTO criarRegraDTO() {
        RegrasDTO regrasDTO = new RegrasDTO();
        regrasDTO.setCategoria("Computador");
        regrasDTO.setPariedade(10);
        return regrasDTO;
    }

    private Regras criarRegra() {
        Regras regras = new Regras();
        regras.setId(1L);
        regras.setCategoria("Computador");
        regras.setPariedade(10);
        return regras;
    }

    @Test
    void testSalvarRegrasComSucesso() {
        RegrasDTO regrasDTO = criarRegraDTO();
        Regras regraSalva = regrasDTO.paraRegras();

        when(repositorioMock.save(regrasDTO.paraRegras())).thenReturn(regraSalva);

        RegrasDTO dtoSalvo = servico.salvar(regrasDTO);

        assertThat(dtoSalvo).isNotNull();
        assertThat(dtoSalvo.getId()).isEqualTo(regraSalva.getId());
    }

    @Test
    void testSalvarRegrasExcecaoCampoNulo() {
        RegrasDTO regrasDTO = criarRegraDTO();
        regrasDTO.setCategoria(null);

        assertThrows(NullPointerException.class, () -> servico.salvar(regrasDTO));
    }

    @Test
    void testListarRegras() {
        List<Regras> regrasLista = Arrays.asList(criarRegra(), criarRegra());
        when(repositorioMock.findAll()).thenReturn(regrasLista);

        List<RegrasDTO> regrasDTOs = servico.buscarTodasRegras();

        assertThat(regrasDTOs).isNotNull();
        assertThat(regrasDTOs.size()).isEqualTo(2);

        for (int i = 0; i < regrasDTOs.size(); i++) {
            RegrasDTO regrasDTO = regrasDTOs.get(i);
            Regras regra = regrasLista.get(i);

            assertThat(regrasDTO.getId()).isEqualTo(regra.getId());
            assertThat(regrasDTO.getCategoria()).isEqualTo(regra.getCategoria());
            assertThat(regrasDTO.getPariedade()).isEqualTo(regra.getPariedade());
        }
    }

    @Test
    void testAtualizarRegra() {
        Long id = 1L;
        RegrasDTO regrasDTO = criarRegraDTO();
        regrasDTO.setCategoria("Eletrônicos");
        regrasDTO.setPariedade(15);

        Regras regraExistente = criarRegra();
        regraExistente.setId(id);

        when(repositorioMock.findById(id)).thenReturn(Optional.of(regraExistente));
        when(repositorioMock.save(any(Regras.class))).thenReturn(regraExistente);

        RegrasDTO resultadoDTO = servico.atualizar(id, regrasDTO);

        assertNotNull(resultadoDTO);
        assertEquals(regrasDTO.getCategoria(), resultadoDTO.getCategoria());
        assertEquals(regrasDTO.getPariedade(), resultadoDTO.getPariedade());
    }

    @Test
    void testAtualizarRegrasExcecaoCampoNulo() {
        RegrasDTO regrasDTO = criarRegraDTO();
        regrasDTO.setCategoria(null);

        Regras regraExistente = criarRegra();
        regraExistente.setId(1L);


        when(repositorioMock.findById(1L)).thenReturn(Optional.of(regraExistente));

        assertThrows(NullPointerException.class, () -> servico.atualizar(1L, regrasDTO));
    }

    @Test
    void testAtualizarRegrasExcecaoIdInexistente() {
        Long IdInexistente = 10L;

        when(repositorioMock.findById(IdInexistente)).thenReturn(Optional.empty());

        assertThrows(RegraNaoEncontradoExcecao.class, () -> servico.atualizar(IdInexistente, criarRegraDTO()));
    }


    @Test
    void testExcluirRegra() {
        Long id = 1L;
        Regras regra = criarRegra();

        when(repositorioMock.findById(id)).thenReturn(Optional.of(regra));

        servico.excluir(id);

        verify(repositorioMock, times(1)).delete(regra);
    }

    @Test
    void testExcluirRegraExcecaoIdNaoEncontrado() {
        Long id = 1L;

        when(repositorioMock.findById(id)).thenReturn(Optional.empty());
        assertThrows(RegraNaoEncontradoExcecao.class, () -> servico.excluir(id));
    }

}
