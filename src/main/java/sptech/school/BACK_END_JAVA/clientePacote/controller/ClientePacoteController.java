package sptech.school.BACK_END_JAVA.clientePacote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;
import sptech.school.BACK_END_JAVA.clientePacote.service.ClientePacoteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientePacotes")
public class ClientePacoteController {
    private final ClientePacoteService service;

    public ClientePacoteController(ClientePacoteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ClientePacote>> getClientePacote() {
        List<ClientePacote> clientePacotes = service.listar();
        return ResponseEntity.ok(clientePacotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientePacote> getById(@PathVariable UUID id) {
        ClientePacote clientePacote = service.buscarPorId(id);
        return ResponseEntity.ok(clientePacote);
    }

    @PostMapping
    public ResponseEntity<ClientePacote> criar(@RequestBody ClientePacote clientePacote, @RequestParam UUID clienteId, @RequestParam UUID pacoteId) {

        ClientePacote criado = service.criar(clientePacote, clienteId, pacoteId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientePacote> atualizar(@PathVariable UUID id, @RequestBody ClientePacote clientePacote) {

        ClientePacote atualizado = service.atualizar(id, clientePacote);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    //funÃ§Ãµes
}


