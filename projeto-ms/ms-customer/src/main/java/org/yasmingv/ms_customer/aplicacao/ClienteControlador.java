package org.yasmingv.ms_customer.aplicacao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_customer.aplicacao.dto.ClienteDTO;
import org.yasmingv.ms_customer.dominio.Cliente;

@RestController
@RequestMapping("/v1/customers")
@RequiredArgsConstructor
public class ClienteControlador {

    private final ClienteServico servico;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.paraCliente();
        Cliente svCliente = servico.salvar(cliente);
        return ResponseEntity.ok(svCliente);
    }

}
