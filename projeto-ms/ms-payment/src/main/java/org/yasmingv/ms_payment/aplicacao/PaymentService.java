package org.yasmingv.ms_payment.aplicacao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yasmingv.ms_payment.aplicacao.dto.PagamentoDTO;
import org.yasmingv.ms_payment.dominio.Pagamento;
import org.yasmingv.ms_payment.infra.PagamentoRepositorio;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PagamentoRepositorio repositorio;

    @Transactional
    public PagamentoDTO salvar(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = pagamentoDTO.paraPagamento();
        Pagamento vpag = repositorio.save(pagamento);

        return new PagamentoDTO(vpag);
    }
}
