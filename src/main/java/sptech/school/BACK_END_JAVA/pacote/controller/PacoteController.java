package sptech.school.BACK_END_JAVA.pacote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.pacote.entity.dto.request.PacoteRequestDto;
import sptech.school.BACK_END_JAVA.pacote.service.PacoteService;
import jakarta.validation.Valid;

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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pacote> criar(@Valid @RequestBody PacoteRequestDto dto) {
        Pacote criado = service.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Pacote> atualizar(@PathVariable UUID id, @Valid @RequestBody PacoteRequestDto dto) {
        Pacote atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funções
}
