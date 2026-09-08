package sptech.school.BACK_END_JAVA.clientePacote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;
import sptech.school.BACK_END_JAVA.clientePacote.service.ClientePacoteService;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clientePacotes")
@PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
public class ClientePacoteController {
    private final ClientePacoteService service;
    private final UsuarioRepository usuarioRepository;

    public ClientePacoteController(ClientePacoteService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<ClientePacote>> getClientePacote() {
        List<ClientePacote> clientePacotes = service.listar();
        return ResponseEntity.ok(clientePacotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientePacote> getById(@PathVariable UUID id) {
        ClientePacote clientePacote = service.buscarPorId(id);
        return ResponseEntity.ok(clientePacote);
    }

    @GetMapping("/meus/{usuarioId}")
    @PreAuthorize("hasAnyRole('CLIENTE', 'ADMIN')")
    public ResponseEntity<List<ClientePacote>> meusPacotes(
            @PathVariable UUID usuarioId,
            Authentication authentication) {
        boolean admin = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        boolean proprioUsuario = usuarioRepository.findByEmail(authentication.getName())
                .map(usuario -> usuario.getId().equals(usuarioId))
                .orElse(false);

        if (!admin && !proprioUsuario) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<ClientePacote> criar(@RequestBody ClientePacote clientePacote, @RequestParam UUID clienteId, @RequestParam UUID pacoteId) {

        ClientePacote criado = service.criar(clientePacote, clienteId, pacoteId);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientePacote> atualizar(@PathVariable UUID id, @RequestBody ClientePacote clientePacote) {

        ClientePacote atualizado = service.atualizar(id, clientePacote);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    //funções
}
