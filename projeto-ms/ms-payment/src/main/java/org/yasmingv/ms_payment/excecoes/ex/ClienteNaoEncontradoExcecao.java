package org.yasmingv.ms_payment.excecoes.ex;

public class ClienteNaoEncontradoExcecao extends RuntimeException {
    public ClienteNaoEncontradoExcecao(String mensagem) {
        super(mensagem);
    }
}