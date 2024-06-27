package org.yasmingv.ms_customer.aplicacao;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yasmingv.ms_customer.aplicacao.dto.ClienteDTO;

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
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO svClienteDTO = servico.salvar(clienteDTO);
        return ResponseEntity.ok(svClienteDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarCliente(@PathVariable Long id) {
        ClienteDTO clienteDTO = servico.buscarPorId(id);
        return ResponseEntity.ok(clienteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO atualizarClienteDTO = servico.atualizar(id, clienteDTO);
        return ResponseEntity.ok(atualizarClienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
