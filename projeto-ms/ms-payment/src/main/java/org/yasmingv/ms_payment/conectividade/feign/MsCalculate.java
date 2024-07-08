package org.yasmingv.ms_payment.conectividade.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_payment.conectividade.feign.dto.CalculadorDTO;
import org.yasmingv.ms_payment.conectividade.feign.dto.CalculadorResposta;

@FeignClient(value = "ms-calculate", path = "/v1/calculate")
public interface MsCalculate {

    @PostMapping
    ResponseEntity<CalculadorResposta> calcularPontos(@RequestBody CalculadorDTO calculadorDTO);

}
