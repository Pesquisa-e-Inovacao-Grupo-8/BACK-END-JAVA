package sptech.school.BACK_END_JAVA.profissional.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.entity.dto.request.ProfissionalRequestDto;
import sptech.school.BACK_END_JAVA.profissional.entity.dto.request.ProfissionalUpdateRequestDto;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.usuario.repository.UsuarioRepository;

import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Profissional buscarOuCriarPorEmail(String email) {
        return profissionalRepository.findByUsuario_Email(email)
                .orElseGet(() -> {
                    var usuario = usuarioRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado"));
                    if (!"PROFISSIONAL".equalsIgnoreCase(usuario.getTipo())) {
                        throw new IllegalStateException("O usuário autenticado não possui perfil profissional");
                    }

                    Profissional profissional = new Profissional();
                    profissional.setUsuario(usuario);
                    profissional.setEspecialidade("Pendente");
                    return profissionalRepository.save(profissional);
                });
    }

    public Profissional criar(ProfissionalRequestDto dto, UUID usuarioId) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Profissional profissional = new Profissional();
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setDescricao(dto.getDescricao());
        profissional.setFoto(dto.getFoto());
        profissional.setUsuario(usuario);
        return profissionalRepository.save(profissional);
    }

    public Profissional atualizar(UUID id, ProfissionalUpdateRequestDto dto) {
        Profissional profissional = buscarPorId(id);
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setDescricao(dto.getDescricao());
        profissional.setFoto(dto.getFoto());
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
