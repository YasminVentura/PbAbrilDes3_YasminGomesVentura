package org.yasmingv.ms_customer.unitario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.yasmingv.ms_customer.aplicacao.ClienteServico;
import org.yasmingv.ms_customer.aplicacao.dto.ClienteDTO;
import org.yasmingv.ms_customer.dominio.Cliente;
import org.yasmingv.ms_customer.infra.ClienteRepositorio;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class ClienteServicoTest {

    @Mock
    private ClienteRepositorio repositorioMock;

    @InjectMocks
    private ClienteServico clienteServico;

    private ClienteDTO criarClienteDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("12345678900");
        clienteDTO.setNome("João");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setAniversario(LocalDate.of(1990, 1, 1));
        clienteDTO.setEmail("joao@email.com");
        clienteDTO.setUrl_foto("url");
        return clienteDTO;
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setCpf("12345678900");
        cliente.setNome("João");
        cliente.setGenero("Masculino");
        cliente.setAniversario(LocalDate.of(1990, 1, 1));
        cliente.setEmail("joao@email.com");
        cliente.setUrl_foto("url");
        return cliente;
    }

    @Test
    void testSalvarCliente() {
        ClienteDTO clienteDTO = criarClienteDTO();
        Cliente cliente = clienteDTO.paraCliente();

        when(repositorioMock.findByEmail(clienteDTO.getEmail())).thenReturn(Optional.empty());
        when(repositorioMock.save(any(Cliente.class))).thenReturn(cliente);

        ClienteDTO resultadoDTO = clienteServico.salvar(clienteDTO);

        assertNotNull(resultadoDTO);
        assertEquals(clienteDTO.getEmail(), resultadoDTO.getEmail());
    }

    @Test
    void testBuscarClientePorIdExistente() {
        Long id = 1L;
        Cliente cliente = criarCliente();

        when(repositorioMock.findById(id)).thenReturn(Optional.of(cliente));

        ClienteDTO resultadoDTO = clienteServico.buscarPorId(id);

        assertNotNull(resultadoDTO);
        assertEquals(cliente.getId(), resultadoDTO.getId());
    }

    @Test
    void testBuscarClientePorIdNaoExistente() {
        Long id = 999L;

        when(repositorioMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> clienteServico.buscarPorId(id));
    }

    @Test
    void testAtualizarCliente() {
        Long id = 1L;
        ClienteDTO clienteDTO = criarClienteDTO();
        clienteDTO.setNome("João Silva");

        Cliente clienteExistente = criarCliente();
        clienteExistente.setId(id);

        when(repositorioMock.findById(id)).thenReturn(Optional.of(clienteExistente));
        when(repositorioMock.save(any(Cliente.class))).thenReturn(clienteExistente);

        ClienteDTO resultadoDTO = clienteServico.atualizar(id, clienteDTO);

        assertNotNull(resultadoDTO);
        assertEquals(clienteDTO.getNome(), resultadoDTO.getNome());
    }

    @Test
    void testExcluirCliente() {
        Long id = 1L;
        Cliente cliente = criarCliente();

        when(repositorioMock.findById(id)).thenReturn(Optional.of(cliente));

        clienteServico.excluir(id);

        verify(repositorioMock, times(1)).delete(cliente);
    }
}
