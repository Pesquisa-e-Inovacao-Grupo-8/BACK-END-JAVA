package sptech.school.BACK_END_JAVA.profissional.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.profissional.service.ProfissionalService;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {
    private final ProfissionalService service;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    // Buscar os serviços marcados pelo profissional
    @GetMapping("/meus-servicos/{usuarioId}")
    public ResponseEntity<List<Servico>> getMeusServicos(@PathVariable UUID usuarioId, Authentication authentication) {
        if (!podeAcessarUsuario(usuarioId, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<Profissional> profOpt = profissionalRepository.findByUsuarioId(usuarioId);

        if (profOpt.isPresent()) {
            return ResponseEntity.ok(profOpt.get().getServicos());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/vincular-servicos/{usuarioId}")
    @PreAuthorize("hasAnyRole('PROFISSIONAL', 'ADMIN')")
    public ResponseEntity<Void> vincularServicos(@PathVariable UUID usuarioId, @RequestBody List<UUID> servicosIds, Authentication authentication) {
        if (!podeAcessarUsuario(usuarioId, authentication)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<Profissional> profOpt = profissionalRepository.findByUsuarioId(usuarioId);

        if (profOpt.isPresent()) {
            Profissional profissional = profOpt.get();

            // Busca todos os serviços no banco correspondentes aos IDs recebidos
            List<Servico> serviçosSelecionados = servicoRepository.findAllById(servicosIds);

            // Atualiza a lista do profissional e salva
            profissional.setServicos(serviçosSelecionados);
            profissionalRepository.save(profissional);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private boolean podeAcessarUsuario(UUID usuarioId, Authentication authentication) {
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        if (admin) return true;

        return usuarioRepository.findByEmail(authentication.getName())
                .map(usuario -> usuario.getId().equals(usuarioId))
                .orElse(false);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Profissional> criar(
            @RequestBody Profissional profissional,
            @RequestParam UUID usuarioId) {

        Profissional criado = service.criar(profissional, usuarioId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Profissional> atualizar(
            @PathVariable UUID id,
            @RequestBody Profissional profissional) {

        Profissional atualizado = service.atualizar(id, profissional);
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
