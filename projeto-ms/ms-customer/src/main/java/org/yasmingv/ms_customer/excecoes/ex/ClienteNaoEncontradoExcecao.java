package org.yasmingv.ms_customer.excecoes.ex;

public class ClienteNaoEncontradoExcecao extends RuntimeException {
    public ClienteNaoEncontradoExcecao(String mensagem) {
        super(mensagem);
    }
}