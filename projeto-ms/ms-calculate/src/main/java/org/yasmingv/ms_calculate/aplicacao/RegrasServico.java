package org.yasmingv.ms_calculate.aplicacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yasmingv.ms_calculate.aplicacao.dto.RegrasDTO;
import org.yasmingv.ms_calculate.dominio.Regras;
import org.yasmingv.ms_calculate.infra.RegrasRepositorio;

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
}
