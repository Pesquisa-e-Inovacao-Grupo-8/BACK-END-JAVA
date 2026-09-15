package sptech.school.BACK_END_JAVA.pacoteServico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.PacoteServico;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.dto.request.PacoteServicoRequestDto;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.dto.request.PacoteServicoUpdateDto;
import sptech.school.BACK_END_JAVA.pacoteServico.service.PacoteServicoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pacoteServicos")
public class PacoteServicoController {
    private final PacoteServicoService service;

    public PacoteServicoController(PacoteServicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PacoteServico>> getPacoteServico() {
        List<PacoteServico> pacoteServicos = service.listar();
        return ResponseEntity.ok(pacoteServicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacoteServico> getById(@PathVariable UUID id) {
        PacoteServico ps = service.buscarPorId(id);
        return ResponseEntity.ok(ps);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PacoteServico> criar(
            @Valid @RequestBody PacoteServicoRequestDto dto,
            @RequestParam UUID pacoteId,
            @RequestParam UUID servicoId) {

        PacoteServico criado = service.criar(dto, pacoteId, servicoId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PacoteServico> atualizar(
            @PathVariable UUID id,
            @Valid @RequestBody PacoteServicoUpdateDto dto) {

        PacoteServico atualizado = service.atualizar(id, dto);
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
