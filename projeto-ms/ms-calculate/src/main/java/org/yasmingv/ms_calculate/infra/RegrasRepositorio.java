package org.yasmingv.ms_calculate.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmingv.ms_calculate.dominio.Regras;

public interface RegrasRepositorio extends JpaRepository<Regras, Long> {

}
