package sptech.school.BACK_END_JAVA.profissional.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProfissionalService {
    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;

    public ProfissionalService(ProfissionalRepository profissionalRepository, UsuarioRepository usuarioRepository) {
        this.profissionalRepository = profissionalRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Profissional> listar() {return profissionalRepository.findAll();}
    public Profissional buscarPorId(UUID id) {
        return profissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
    }

    public Profissional criar(Profissional profissional, UUID usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        profissional.setUsuario(usuario);
        return profissionalRepository.save(profissional);
    }

    public Profissional atualizar(UUID id, Profissional profissional) {
        if (!profissionalRepository.existsById(id)) {
            throw new RuntimeException("Profissional não encontrado");
        }
        profissional.setId(id);
        return profissionalRepository.save(profissional);
    }

    public void deletar(UUID id) {
        if (!profissionalRepository.existsById(id)) {
            throw new RuntimeException("Profissional não encontrado");
        }
        profissionalRepository.deleteById(id);
    }
        //restante das funções
}
