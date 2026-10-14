package dev.Zerphyis.wallet.Infra.Controller;

import dev.Zerphyis.wallet.Application.Dto.ClienteRequestDto;
import dev.Zerphyis.wallet.Application.UseCases.ClienteService;
import dev.Zerphyis.wallet.Domain.Entitys.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@RequestBody ClienteRequestDto requestDTO) {
        Cliente clienteCriado = service.cadastrar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = service.listar();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable UUID id) {
        Cliente cliente = service.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable UUID id, @RequestBody ClienteRequestDto requestDTO) {
        Cliente clienteAtualizado = service.atualizar(id, requestDTO);
        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}


