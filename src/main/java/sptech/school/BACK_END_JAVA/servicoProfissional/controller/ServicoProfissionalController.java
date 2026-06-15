package sptech.school.BACK_END_JAVA.servicoProfissional.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.servicoProfissional.service.ServicoProfissionalService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/profissionais")
public class ServicoProfissionalController {

    private final ServicoProfissionalService service;

    public ServicoProfissionalController(ServicoProfissionalService service) {
        this.service = service;
    }

    @PostMapping("/{profissionalId}/servicos")
    public ResponseEntity<Void> vincularServicos(
            @PathVariable UUID profissionalId,
            @RequestBody List<UUID> servicosIds
    ) {
        System.out.println("IDS RECEBIDOS: " + servicosIds);
        service.vincularServicos(profissionalId, servicosIds);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{profissionalId}/servicos")
    public ResponseEntity<List<?>> listarServicos(@PathVariable UUID profissionalId) {
        return ResponseEntity.ok(service.listarPorProfissional(profissionalId));
    }

    @DeleteMapping("/{profissionalId}/servicos/{servicoId}")
    public ResponseEntity<Void> removerServico(
            @PathVariable UUID profissionalId,
            @PathVariable UUID servicoId
    ) {
        service.remover(profissionalId, servicoId);
        return ResponseEntity.noContent().build();
    }
}