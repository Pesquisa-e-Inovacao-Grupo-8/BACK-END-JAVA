package sptech.school.BACK_END_JAVA.profissional.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.service.ProfissionalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {
    private final ProfissionalService service;

    public ProfissionalController(ProfissionalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Profissional>> getProfissionais() {
        List<Profissional> profissionais = service.listar();
        return ResponseEntity.ok(profissionais);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> getById(@PathVariable UUID id) {
        Profissional prof = service.buscarPorId(id);
        return ResponseEntity.ok(prof);
    }

    @PostMapping
    public ResponseEntity<Profissional> criar(
            @RequestBody Profissional profissional,
            @RequestParam UUID usuarioId) {

        Profissional criado = service.criar(profissional, usuarioId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> atualizar(
            @PathVariable UUID id,
            @RequestBody Profissional profissional) {

        Profissional atualizado = service.atualizar(id, profissional);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funções
}
