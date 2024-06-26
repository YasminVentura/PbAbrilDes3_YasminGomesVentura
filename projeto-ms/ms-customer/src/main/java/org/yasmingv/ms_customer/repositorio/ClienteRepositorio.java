package org.yasmingv.ms_customer.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmingv.ms_customer.dominio.Cliente;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
}
