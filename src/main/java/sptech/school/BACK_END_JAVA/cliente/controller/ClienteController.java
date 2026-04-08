package sptech.school.BACK_END_JAVA.cliente.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;
import sptech.school.BACK_END_JAVA.cliente.entity.dto.request.ClienteRequestDto;
import sptech.school.BACK_END_JAVA.cliente.service.ClienteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getCliente() {
        List<Cliente> clientes = service.listar();
        return ResponseEntity.ok(clientes);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable UUID id) {
        Cliente cliente = service.buscarPorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody ClienteRequestDto dto) {

        Cliente cliente = new Cliente();
        cliente.setObservacoes(dto.getObservacoes());

        Cliente criado = service.criar(cliente, dto.getUsuarioId());

        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable UUID id, @RequestBody Cliente cliente) {

        Cliente atualizado = service.atualizar(id, cliente);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    //funções
}
