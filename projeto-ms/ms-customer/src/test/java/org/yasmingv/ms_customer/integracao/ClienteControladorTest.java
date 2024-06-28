package org.yasmingv.ms_customer.integracao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.yasmingv.ms_customer.aplicacao.ClienteControlador;
import org.yasmingv.ms_customer.aplicacao.ClienteServico;
import org.yasmingv.ms_customer.aplicacao.dto.ClienteDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClienteControladorTest {

    @InjectMocks
    private ClienteControlador clienteControlador;

    @Mock
    private ClienteServico clienteServico;

    @Test
    void criarCliente_valido_retornarSTTS200() {
        ClienteDTO clienteDTO = new ClienteDTO();

        ClienteDTO svClienteDTO = new ClienteDTO();
        Mockito.when(clienteServico.salvar(clienteDTO)).thenReturn(svClienteDTO);

        ResponseEntity<ClienteDTO> response = clienteControlador.criarCliente(clienteDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(svClienteDTO, response.getBody());
    }

}
