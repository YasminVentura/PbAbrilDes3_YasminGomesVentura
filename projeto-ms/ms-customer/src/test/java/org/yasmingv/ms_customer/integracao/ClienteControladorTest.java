package org.yasmingv.ms_customer.integracao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.yasmingv.ms_customer.aplicacao.ClienteControlador;
import org.yasmingv.ms_customer.aplicacao.ClienteServico;
import org.yasmingv.ms_customer.aplicacao.dto.ClienteDTO;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControladorTest {

    @InjectMocks
    private ClienteControlador clienteControlador;

    @Mock
    private ClienteServico clienteServico;

    private ClienteDTO criarClienteDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setCpf("123.456.789-00");
        clienteDTO.setNome("Marcos Bandrão");
        clienteDTO.setGenero("Masculino");
        clienteDTO.setAniversario(LocalDate.of(1990, 1, 1));
        clienteDTO.setEmail("marcos.band@exemplo.com");
        clienteDTO.setUrl_foto("examplofoto.jpg");
        return clienteDTO;
    }

    @Test
    void criarCliente_valido_retornarSTTS201() {
        ClienteDTO clienteDTO = criarClienteDTO();

        ClienteDTO clienteSalvoDTO = criarClienteDTO();
        clienteSalvoDTO.setId(1L);

        when(clienteServico.salvar(any(ClienteDTO.class))).thenReturn(clienteSalvoDTO);


        ResponseEntity<ClienteDTO> responseEntity = clienteControlador.criarCliente(clienteDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        ClienteDTO responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertEquals(1L, responseBody.getId());
        assertEquals("Marcos Bandrão", responseBody.getNome());
        assertEquals("123.456.789-00", responseBody.getCpf());
        assertEquals("Masculino", responseBody.getGenero());
        assertEquals(LocalDate.of(1990, 1, 1), responseBody.getAniversario());
        assertEquals("marcos.band@exemplo.com", responseBody.getEmail());
        assertEquals("examplofoto.jpg", responseBody.getUrl_foto());
    }

    @Test
    void buscarCliente_retornarSTTS200() {
        Long id = 1L;
        ClienteDTO clienteDTO = criarClienteDTO();
        clienteDTO.setId(id);

        when(clienteServico.buscarPorId(id)).thenReturn(clienteDTO);


        ResponseEntity<ClienteDTO> responseEntity = clienteControlador.buscarCliente(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ClienteDTO responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertNotNull(responseBody);
        assertEquals(clienteDTO.getCpf(), responseBody.getCpf());
        assertEquals(clienteDTO.getNome(), responseBody.getNome());
        assertEquals(clienteDTO.getGenero(), responseBody.getGenero());
        assertEquals(clienteDTO.getAniversario(), responseBody.getAniversario());
        assertEquals(clienteDTO.getEmail(), responseBody.getEmail());
        assertEquals(clienteDTO.getUrl_foto(), responseBody.getUrl_foto());
    }

    @Test
    void atualizarCliente_retorarSTTS200() {
        Long id = 1L;
        ClienteDTO clienteDTO = criarClienteDTO();

        ClienteDTO clienteAtualizadoDTO = criarClienteDTO();
        clienteAtualizadoDTO.setId(id);
        clienteAtualizadoDTO.setNome("Marcos Silva");

        when(clienteServico.atualizar(any(Long.class), any(ClienteDTO.class))).thenReturn(clienteAtualizadoDTO);

        ResponseEntity<ClienteDTO> responseEntity = clienteControlador.atualizarCliente(id, clienteDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        ClienteDTO responseBody = responseEntity.getBody();
        assert responseBody != null;
        assertEquals(id, responseBody.getId());
        assertEquals("Marcos Silva", responseBody.getNome());
        assertEquals("123.456.789-00", responseBody.getCpf());
        assertEquals("Masculino", responseBody.getGenero());
        assertEquals(LocalDate.of(1990, 1, 1), responseBody.getAniversario());
        assertEquals("marcos.band@exemplo.com", responseBody.getEmail());
        assertEquals("examplofoto.jpg", responseBody.getUrl_foto());
    }

    @Test
    void deletarCliente_retornarSTTS204(){

    }

}
