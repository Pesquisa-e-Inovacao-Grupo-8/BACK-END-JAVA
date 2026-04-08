package sptech.school.BACK_END_JAVA.servico.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ServicoService {
    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> listar() {return servicoRepository.findAll();}

    public Servico buscarPorId(UUID id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    public Servico criar(Servico servico) {
        return servicoRepository.save(servico);
    }

    public Servico atualizar(UUID id, Servico servico) {
        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado");
        }
        servico.setId(id);
        return servicoRepository.save(servico);
    }

    public void deletar(UUID id) {
        if (!servicoRepository.existsById(id)) {
            throw new RuntimeException("Serviço não encontrado");
        }
        servicoRepository.deleteById(id);
    }
    //restante das funções
}
