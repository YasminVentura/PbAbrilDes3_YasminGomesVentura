package org.yasmingv.ms_payment.excecoes.ex;

public class IdNaoEncontradoExcecao extends RuntimeException {
    public IdNaoEncontradoExcecao(String mensagem) {
        super(mensagem);
    }
}