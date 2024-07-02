package org.yasmingv.ms_payment.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmingv.ms_payment.dominio.Pagamento;

import java.util.UUID;

public interface PagamentoRepositorio extends JpaRepository<Pagamento, UUID>{

}
