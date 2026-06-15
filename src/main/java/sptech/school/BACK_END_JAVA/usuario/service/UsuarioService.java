package sptech.school.BACK_END_JAVA.usuario.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario criar(Usuario usuario) {

        usuario.setSenha(
                passwordEncoder.encode(usuario.getSenha())
        );

        usuario.setCriacao(LocalDateTime.now());

        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(UUID id, Usuario usuario) {

        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        existente.setNome(usuario.getNome());
        existente.setTelefone(usuario.getTelefone());
        existente.setCpf(usuario.getCpf());
        existente.setEmail(usuario.getEmail());
        existente.setTipo(usuario.getTipo());
        existente.setAtivo(usuario.getAtivo());

        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            existente.setSenha(
                    passwordEncoder.encode(usuario.getSenha())
            );
        }

        return usuarioRepository.save(existente);
    }

    public void deletar(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }

        usuarioRepository.deleteById(id);
    }
}