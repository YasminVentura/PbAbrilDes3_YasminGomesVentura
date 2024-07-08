package org.yasmingv.ms_payment.conectividade.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_payment.conectividade.feign.dto.ClienteDTO;

@FeignClient(value = "ms-customer", path = "/v1/customers")
public interface MsCustomer {

    @GetMapping("/{id}")
    ResponseEntity<ClienteDTO> buscarCliente(@PathVariable Long id);

}
