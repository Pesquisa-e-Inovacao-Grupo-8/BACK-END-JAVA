package sptech.school.BACK_END_JAVA.usuario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")
@PreAuthorize("hasAnyRole('CLIENTE', 'PROFISSIONAL', 'ADMIN')")
public class MinhaContaController {
    private final UsuarioRepository usuarioRepository;

    public MinhaContaController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/me")
    public ResponseEntity<Usuario> meusDados(Authentication authentication) {
        return usuarioRepository.findByEmail(authentication.getName())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/me")
    public ResponseEntity<Usuario> atualizarMeusDados(
            @RequestBody Usuario dados,
            Authentication authentication) {
        return usuarioRepository.findByEmail(authentication.getName())
                .map(usuario -> {
                    usuario.setNome(dados.getNome());
                    usuario.setTelefone(dados.getTelefone());
                    return ResponseEntity.ok(usuarioRepository.save(usuario));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
