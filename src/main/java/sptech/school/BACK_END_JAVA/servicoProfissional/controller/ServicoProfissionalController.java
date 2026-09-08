package sptech.school.BACK_END_JAVA.servicoProfissional.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.servicoProfissional.service.ServicoProfissionalService;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/profissionais")
public class ServicoProfissionalController {

    private final ServicoProfissionalService service;
    private final ProfissionalRepository profissionalRepository;

    public ServicoProfissionalController(ServicoProfissionalService service,
                                         ProfissionalRepository profissionalRepository) {
        this.service = service;
        this.profissionalRepository = profissionalRepository;
    }

    @PostMapping("/{profissionalId}/servicos")
    @PreAuthorize("hasAnyRole('PROFISSIONAL', 'ADMIN')")
    public ResponseEntity<Void> vincularServicos(
            @PathVariable UUID profissionalId,
            @RequestBody List<UUID> servicosIds,
            Authentication authentication
    ) {
        if (!podeAcessar(profissionalId, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        System.out.println("IDS RECEBIDOS: " + servicosIds);
        service.vincularServicos(profissionalId, servicosIds);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{profissionalId}/servicos")
    public ResponseEntity<List<?>> listarServicos(@PathVariable UUID profissionalId) {
        return ResponseEntity.ok(service.listarServicosPorProfissional(profissionalId));
    }

    @DeleteMapping("/{profissionalId}/servicos/{servicoId}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL', 'ADMIN')")
    public ResponseEntity<Void> removerServico(
            @PathVariable UUID profissionalId,
            @PathVariable UUID servicoId,
            Authentication authentication
    ) {
        if (!podeAcessar(profissionalId, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        service.remover(profissionalId, servicoId);
        return ResponseEntity.noContent().build();
    }

    private boolean podeAcessar(UUID profissionalId, Authentication authentication) {
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        if (admin) return true;

        return profissionalRepository.findById(profissionalId)
                .map(profissional -> profissional.getUsuario() != null
                        && authentication.getName().equals(profissional.getUsuario().getEmail()))
                .orElse(false);
    }
}