package sptech.school.BACK_END_JAVA.usuario.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {return usuarioRepository.findAll();}

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public Usuario criar(Usuario usuario) {
        usuario.setCriacao(LocalDateTime.now()); // define data de criação
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizar(UUID id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }

    public void deletar(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
    //restante das funções
}
