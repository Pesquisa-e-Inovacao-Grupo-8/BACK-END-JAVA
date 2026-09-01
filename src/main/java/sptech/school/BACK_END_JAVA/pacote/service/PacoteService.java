package sptech.school.BACK_END_JAVA.pacote.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
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

    public Pacote criar(Pacote pacote) {
        return pacoteRepository.save(pacote);
    }

    public Pacote atualizar(UUID id, Pacote pacote) {
        if (!pacoteRepository.existsById(id)) {
            throw new RuntimeException("Pacote não encontrado");
        }
        pacote.setId(id);
        return pacoteRepository.save(pacote);
    }

    public void deletar(UUID id) {
        if (!pacoteRepository.existsById(id)) {
            throw new RuntimeException("Pacote não encontrado");
        }
        pacoteRepository.deleteById(id);
    }
}
