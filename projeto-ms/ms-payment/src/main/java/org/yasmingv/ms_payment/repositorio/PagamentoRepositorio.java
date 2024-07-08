package org.yasmingv.ms_payment.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmingv.ms_payment.dominio.Pagamento;

import java.util.List;
import java.util.UUID;

public interface PagamentoRepositorio extends JpaRepository<Pagamento, UUID>{

    List<Pagamento> findByClienteId(Long id);
}
