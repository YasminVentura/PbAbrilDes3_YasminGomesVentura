package org.yasmingv.ms_customer.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yasmingv.ms_customer.dominio.Cliente;

import java.util.Optional;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
}
