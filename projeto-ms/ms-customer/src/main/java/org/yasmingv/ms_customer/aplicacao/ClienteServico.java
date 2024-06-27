package org.yasmingv.ms_customer.aplicacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yasmingv.ms_customer.dominio.Cliente;
import org.yasmingv.ms_customer.infra.ClienteRepositorio;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteServico {

    private final ClienteRepositorio repositorio;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        if (repositorio.findByEmail(cliente.getEmail()).isPresent()) {
            throw new RuntimeException("Email já existe");
        }
        return repositorio.save(cliente);
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return repositorio.findById(id);
    }
}
