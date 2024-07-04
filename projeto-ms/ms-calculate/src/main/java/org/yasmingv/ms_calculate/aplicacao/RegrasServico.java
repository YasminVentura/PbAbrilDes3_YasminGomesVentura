package org.yasmingv.ms_calculate.aplicacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yasmingv.ms_calculate.aplicacao.dto.RegrasDTO;
import org.yasmingv.ms_calculate.dominio.Regras;
import org.yasmingv.ms_calculate.excecoes.ex.RegraNaoEncontradoExcecao;
import org.yasmingv.ms_calculate.infra.RegrasRepositorio;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegrasServico {

    private final RegrasRepositorio repositorio;

    @Transactional
    public RegrasDTO salvar(RegrasDTO regrasDTO) {
        Regras regras = regrasDTO.paraRegras();
        Regras vregras = repositorio.save(regras);

        return new RegrasDTO(vregras);
    }

    public List<RegrasDTO> buscarTodasRegras() {
        List<Regras> regras = repositorio.findAll();

        return regras.stream()
                .map(RegrasDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public RegrasDTO atualizar(Long id, RegrasDTO regrasDTO) {
        Regras regraExistente = repositorio.findById(id)
                .orElseThrow(() -> new RegraNaoEncontradoExcecao("Regra não encontrada"));

        regraExistente.setCategoria(regrasDTO.getCategoria());
        regraExistente.setPariedade(regrasDTO.getPariedade());

        Regras regras = repositorio.save(regraExistente);

        return new RegrasDTO(regras);
    }

    @Transactional
    public void excluir(Long id) {
        Regras regras = repositorio.findById(id)
                .orElseThrow(() -> new RegraNaoEncontradoExcecao("Regra não encontrada"));

        repositorio.delete(regras);
    }

}
