package sptech.school.BACK_END_JAVA.comprovante.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.comprovante.entity.Comprovante;
import sptech.school.BACK_END_JAVA.comprovante.service.ComprovanteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/comprovantes")
public class ComprovanteController {
    private final ComprovanteService service;

    public ComprovanteController(ComprovanteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Comprovante>> getComprovante() {
        List<Comprovante> comprovantes = service.listar();
        return ResponseEntity.ok(comprovantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comprovante> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Comprovante> criar(@RequestBody Comprovante comprovante, @RequestParam UUID pagamentoId) {

        Comprovante criado = service.criar(comprovante, pagamentoId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comprovante> atualizar(@PathVariable UUID id, @RequestBody Comprovante comprovante) {

        Comprovante atualizado = service.atualizar(id, comprovante);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    //funÃ§Ãµes
}


