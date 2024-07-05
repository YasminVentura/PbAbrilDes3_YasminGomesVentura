package org.yasmingv.ms_payment.excecoes;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class MensagemErro {
    private LocalDateTime timestamp;

    private int status;

    private String error;

    private String mensagem;

    private Map<String, String> errors;
}
