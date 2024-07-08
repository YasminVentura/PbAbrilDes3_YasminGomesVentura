package org.yasmingv.ms_payment.unitario;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.yasmingv.ms_payment.conectividade.feign.MsCalculate;
import org.yasmingv.ms_payment.conectividade.feign.MsCustomer;
import org.yasmingv.ms_payment.conectividade.feign.dto.CalculadorDTO;
import org.yasmingv.ms_payment.conectividade.feign.dto.CalculadorResposta;
import org.yasmingv.ms_payment.conectividade.feign.dto.ClienteDTO;
import org.yasmingv.ms_payment.conectividade.mqueue.PontosMensagem;
import org.yasmingv.ms_payment.dominio.Pagamento;
import org.yasmingv.ms_payment.dominio.dto.PagamentoDTO;
import org.yasmingv.ms_payment.excecoes.ex.IdNaoEncontradoExcecao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.yasmingv.ms_payment.repositorio.PagamentoRepositorio;
import org.yasmingv.ms_payment.servico.PagamentoServico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DirtiesContext
class PagamentoServicoTest {

    @Mock
    private PagamentoRepositorio repositorioMock;

    @Mock
    private MsCustomer msClienteFeignMock;

    @Mock
    private MsCalculate msCalculateFeignMock;

    @Mock
    private PontosMensagem pontosMensagemMock;

    @InjectMocks
    private PagamentoServico pagamentoServico;

    private PagamentoDTO criarPagamentoDTO() {
        PagamentoDTO pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setClienteId(1L);
        pagamentoDTO.setCategoriaId(2L);
        pagamentoDTO.setTotal(100.0);
        return pagamentoDTO;
    }

    private Pagamento criarPagamento() {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(UUID.randomUUID());
        pagamento.setClienteId(1L);
        pagamento.setCategoriaId(2L);
        pagamento.setTotal(100.0);
        pagamento.setDataCriacao(LocalDateTime.now());
        return pagamento;
    }

    private ResponseEntity<ClienteDTO> criarRespostaClienteDTO(boolean sucesso) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(sucesso ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        return builder.body(clienteDTO);
    }

    private ResponseEntity<CalculadorResposta> criarRespostaCalculador(boolean sucesso) {
        CalculadorResposta calculadorResposta = new CalculadorResposta();
        calculadorResposta.setTotal(50.0);
        ResponseEntity.BodyBuilder builder = ResponseEntity.status(sucesso ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        return builder.body(calculadorResposta);
    }

    @Test
    void testSalvarPagamento() throws JsonProcessingException {
        PagamentoDTO pagamentoDTO = criarPagamentoDTO();
        Pagamento pagamento = criarPagamento();
        ResponseEntity<ClienteDTO> respostaClienteDTO = criarRespostaClienteDTO(true);
        ResponseEntity<CalculadorResposta> respostaCalculador = criarRespostaCalculador(true);

        when(msClienteFeignMock.buscarCliente(pagamentoDTO.getClienteId())).thenReturn(respostaClienteDTO);
        when(msCalculateFeignMock.calcularPontos(any(CalculadorDTO.class))).thenReturn(respostaCalculador);
        when(repositorioMock.save(any(Pagamento.class))).thenReturn(pagamento);

        PagamentoDTO resultadoDTO = pagamentoServico.salvar(pagamentoDTO);

        assertNotNull(resultadoDTO);
        assertEquals(pagamentoDTO.getClienteId(), resultadoDTO.getClienteId());
        verify(pontosMensagemMock, times(1)).enviarPontos(any());
    }

    @Test
    void testSalvarPagamentoClienteNaoEncontrado() {
        PagamentoDTO pagamentoDTO = criarPagamentoDTO();
        ResponseEntity<ClienteDTO> respostaClienteDTO = criarRespostaClienteDTO(false);

        when(msClienteFeignMock.buscarCliente(pagamentoDTO.getClienteId())).thenReturn(respostaClienteDTO);

        assertThrows(IdNaoEncontradoExcecao.class, () -> pagamentoServico.salvar(pagamentoDTO));
    }

    /*
    @Test
    void testBuscarPagamentoPorIdExistente() {
        UUID id = UUID.randomUUID();
        Pagamento pagamento = criarPagamento();

        when(repositorioMock.findById(id)).thenReturn(Optional.of(pagamento));

        PagamentoDTO resultadoDTO = pagamentoServico.buscarPagamentoPorId(id);

        assertNotNull(resultadoDTO);
        assertEquals(pagamento.getId(), resultadoDTO.getId());
    }
     */

    @Test
    void testBuscarPagamentoPorIdNaoExistente() {
        UUID id = UUID.randomUUID();

        when(repositorioMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(IdNaoEncontradoExcecao.class, () -> pagamentoServico.buscarPagamentoPorId(id));
    }

    @Test
    void testBuscarClientePorIdExistente() {
        Long clienteId = 1L;
        Pagamento pagamento = criarPagamento();
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(pagamento);

        when(repositorioMock.findByClienteId(clienteId)).thenReturn(pagamentos);

        List<PagamentoDTO> resultadoDTOs = pagamentoServico.buscarClientePorId(clienteId);

        assertEquals(1, resultadoDTOs.size());
        assertEquals(pagamento.getClienteId(), resultadoDTOs.get(0).getClienteId());
    }

    @Test
    void testBuscarClientePorIdNaoEncontrado() {
        Long clienteId = 999L;

        when(repositorioMock.findByClienteId(clienteId)).thenReturn(new ArrayList<>());

        assertThrows(IdNaoEncontradoExcecao.class, () -> pagamentoServico.buscarClientePorId(clienteId));
    }
}
