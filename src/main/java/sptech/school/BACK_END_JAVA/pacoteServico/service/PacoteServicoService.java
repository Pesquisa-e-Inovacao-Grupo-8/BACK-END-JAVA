package sptech.school.BACK_END_JAVA.pacoteServico.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.pacote.repository.PacoteRepository;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.PacoteServico;
import sptech.school.BACK_END_JAVA.pacoteServico.repository.PacoteServicoRepository;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;

import java.util.List;

@Service
public class PacoteServicoService {
    private final PacoteServicoRepository pacoteServicoRepository;
    private final PacoteRepository pacoteRepository;
    private final ServicoRepository servicoRepository;

    public PacoteServicoService(PacoteServicoRepository pacoteServicoRepository, PacoteRepository pacoteRepository, ServicoRepository servicoRepository) {
        this.pacoteServicoRepository = pacoteServicoRepository;
        this.pacoteRepository = pacoteRepository;
        this.servicoRepository = servicoRepository;
    }

    public List<PacoteServico> listar() {return pacoteServicoRepository.findAll();}

    public PacoteServico buscarPorId(Integer id) {
        return pacoteServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PacoteServico não encontrado"));
    }

    public PacoteServico criar(PacoteServico pacoteServico, Integer pacoteId, Integer servicoId) {

        var pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
        var servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Servico não encontrado"));

        pacoteServico.setPacote(pacote);
        pacoteServico.setServico(servico);

        return pacoteServicoRepository.save(pacoteServico);
    }

    public PacoteServico atualizar(Integer id, PacoteServico pacoteServico) {

        if (!pacoteServicoRepository.existsById(id)) {
            throw new RuntimeException("PacoteServico não encontrado");
        }

        pacoteServico.setId(id);
        return pacoteServicoRepository.save(pacoteServico);
    }

    public void deletar(Integer id) {

        if (!pacoteServicoRepository.existsById(id)) {
            throw new RuntimeException("PacoteServico não encontrado");
        }

        pacoteServicoRepository.deleteById(id);
    }
    //restante das funções
}
