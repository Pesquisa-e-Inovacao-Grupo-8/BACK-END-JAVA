package sptech.school.BACK_END_JAVA.pacote.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.pacote.entity.dto.request.PacoteRequestDto;
import sptech.school.BACK_END_JAVA.pacote.repository.PacoteRepository;

import java.util.List;
import java.util.UUID;

@Service
public class PacoteService {
    private final PacoteRepository pacoteRepository;

    public PacoteService(PacoteRepository pacoteRepository) {
        this.pacoteRepository = pacoteRepository;
    }

    public List<Pacote> listar() {return pacoteRepository.findAll();}
    public Pacote buscarPorId(UUID id) {
        return pacoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
    }

    public Pacote criar(PacoteRequestDto dto) {
        Pacote pacote = mapear(dto, new Pacote());
        return pacoteRepository.save(pacote);
    }

    public Pacote atualizar(UUID id, PacoteRequestDto dto) {
        Pacote pacote = buscarPorId(id);
        mapear(dto, pacote);
        return pacoteRepository.save(pacote);
    }

    private Pacote mapear(PacoteRequestDto dto, Pacote pacote) {
        pacote.setNome(dto.getNome());
        pacote.setDescricao(dto.getDescricao());
        pacote.setPrecoTotal(dto.getPrecoTotal());
        return pacote;
    }

    public void deletar(UUID id) {
        if (!pacoteRepository.existsById(id)) {
            throw new RuntimeException("Pacote não encontrado");
        }
        pacoteRepository.deleteById(id);
    }
}
