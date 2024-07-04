package org.yasmingv.ms_calculate.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yasmingv.ms_calculate.excecoes.ex.RegraNaoEncontradoExcecao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensagemErro> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        MensagemErro mensagemErro = new MensagemErro();
        mensagemErro.setTimestamp(LocalDateTime.now());
        mensagemErro.setStatus(HttpStatus.BAD_REQUEST.value());
        mensagemErro.setError("Requisição inválida");
        mensagemErro.setMensagem("Erro de validação");
        mensagemErro.setErrors(errors);

        return new ResponseEntity<>(mensagemErro, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MensagemErro> handleRuntimeExceptions(RuntimeException ex) {
        MensagemErro mensagemErro = new MensagemErro();
        mensagemErro.setTimestamp(LocalDateTime.now());
        mensagemErro.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        mensagemErro.setError("Erro interno do servidor");
        mensagemErro.setMensagem(ex.getMessage());

        return new ResponseEntity<>(mensagemErro, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RegraNaoEncontradoExcecao.class)
    public ResponseEntity<MensagemErro> handleClienteNaoEncontradoExcecao(RegraNaoEncontradoExcecao ex) {
        MensagemErro mensagemErro = new MensagemErro();
        mensagemErro.setTimestamp(LocalDateTime.now());
        mensagemErro.setStatus(HttpStatus.NOT_FOUND.value());
        mensagemErro.setError("Não encontrada");
        mensagemErro.setMensagem(ex.getMessage());

        return new ResponseEntity<>(mensagemErro, HttpStatus.NOT_FOUND);
    }

}
