package org.yasmingv.ms_customer.aplicacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yasmingv.ms_customer.aplicacao.dto.ClienteDTO;
import org.yasmingv.ms_customer.dominio.Cliente;
import org.yasmingv.ms_customer.infra.ClienteRepositorio;

@Service
@RequiredArgsConstructor
public class ClienteServico {

    private final ClienteRepositorio repositorio;

    @Transactional
    public ClienteDTO salvar(ClienteDTO clienteDTO) {
        if (repositorio.findByEmail(clienteDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já existe");
        }

        Cliente cliente = clienteDTO.paraCliente();
        Cliente svCliente = repositorio.save(cliente);

        return new ClienteDTO(svCliente);
    }

    public ClienteDTO buscarPorId(Long id) {
        Cliente cliente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return new ClienteDTO(cliente);
    }

    @Transactional
    public ClienteDTO atualizar(Long id, ClienteDTO clienteDTO) {
        Cliente clienteExistente = repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (!clienteExistente.getEmail().equals(clienteDTO.getEmail()) && repositorio.findByEmail(clienteDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email já existe");
        }

        clienteExistente.setCpf(clienteDTO.getCpf());
        clienteExistente.setNome(clienteDTO.getNome());
        clienteExistente.setGenero(clienteDTO.getGenero());
        clienteExistente.setAniversario(clienteDTO.getAniversario());
        clienteExistente.setEmail(clienteDTO.getEmail());
        clienteExistente.setUrl_foto(clienteDTO.getUrl_foto());

        Cliente updatedCliente = repositorio.save(clienteExistente);

        return new ClienteDTO(updatedCliente);
    }
}
