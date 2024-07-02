package org.yasmingv.ms_payment.aplicacao;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class PagamentoControlador {

    @GetMapping
    public String status(){
        return "ok";
    }
}
