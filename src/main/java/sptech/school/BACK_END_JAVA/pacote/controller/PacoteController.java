package sptech.school.BACK_END_JAVA.pacote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.pacote.service.PacoteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacotes")
public class PacoteController {
    private final PacoteService service;

    public PacoteController(PacoteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Pacote>> getPacote() {
        List<Pacote> pacotes = service.listar();
        return ResponseEntity.ok(pacotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pacote> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Pacote> criar(@RequestBody Pacote pacote) {
        Pacote criado = service.criar(pacote);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pacote> atualizar(@PathVariable UUID id, @RequestBody Pacote pacote) {
        Pacote atualizado = service.atualizar(id, pacote);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funÃ§Ãµes
}


