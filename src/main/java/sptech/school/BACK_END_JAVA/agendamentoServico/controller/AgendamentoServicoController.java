package sptech.school.BACK_END_JAVA.agendamentoServico.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.dto.request.AgendamentoServicoRequestDto;
import sptech.school.BACK_END_JAVA.agendamentoServico.service.AgendamentoServicoService;

import java.util.List;

@RestController
@RequestMapping("/agendamentoServicos")
@CrossOrigin(origins = "*")
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
    public ResponseEntity<AgendamentoServico> getById(@PathVariable Integer id) {
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
    public ResponseEntity<AgendamentoServico> atualizar(@PathVariable Integer id, @RequestBody AgendamentoServicoRequestDto dto) {

        AgendamentoServico atualizado = service.atualizar(
                id,
                dto.getAgendamentoId(),
                dto.getServicoId()
        );

        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    //funÃ§Ãµes
}


