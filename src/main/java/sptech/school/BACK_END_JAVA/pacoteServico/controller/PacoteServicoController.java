package sptech.school.BACK_END_JAVA.pacoteServico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.PacoteServico;
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
    public ResponseEntity<PacoteServico> criar(
            @RequestBody PacoteServico pacoteServico,
            @RequestParam UUID pacoteId,
            @RequestParam UUID servicoId) {

        PacoteServico criado = service.criar(pacoteServico, pacoteId, servicoId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacoteServico> atualizar(
            @PathVariable UUID id,
            @RequestBody PacoteServico pacoteServico) {

        PacoteServico atualizado = service.atualizar(id, pacoteServico);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funções
}
