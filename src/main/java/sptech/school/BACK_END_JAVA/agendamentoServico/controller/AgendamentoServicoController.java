package sptech.school.BACK_END_JAVA.agendamentoServico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.dto.request.AgendamentoServicoRequestDto;
import sptech.school.BACK_END_JAVA.agendamentoServico.service.AgendamentoServicoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/agendamentoServicos")
@PreAuthorize("hasAnyRole('CLIENTE', 'PROFISSIONAL', 'ADMIN')")
public class AgendamentoServicoController {

    private final AgendamentoServicoService service;

    public AgendamentoServicoController(AgendamentoServicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AgendamentoServico>> getAgendamentoServico() {
        List<AgendamentoServico> agendamentoServicos = service.listar();
        return ResponseEntity.ok(agendamentoServicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoServico> getById(@PathVariable UUID id) {
        AgendamentoServico agendamentoServico = service.buscarPorId(id);
        return ResponseEntity.ok(agendamentoServico);
    }

    @PostMapping
    public ResponseEntity<AgendamentoServico> criar(
            @RequestBody AgendamentoServicoRequestDto dto) {

        AgendamentoServico criado = service.criar(
                dto.getAgendamentoId(),
                dto.getServicoId()
        );

        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL', 'ADMIN')")
    public ResponseEntity<AgendamentoServico> atualizar(@PathVariable UUID id, @RequestBody AgendamentoServicoRequestDto dto) {

        AgendamentoServico atualizado = service.atualizar(
                id,
                dto.getAgendamentoId(),
                dto.getServicoId()
        );

        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL', 'ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funções
}
