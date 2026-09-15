package sptech.school.BACK_END_JAVA.pacoteServico.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.pacote.repository.PacoteRepository;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.PacoteServico;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.dto.request.PacoteServicoRequestDto;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.dto.request.PacoteServicoUpdateDto;
import sptech.school.BACK_END_JAVA.pacoteServico.repository.PacoteServicoRepository;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;

import java.util.List;
import java.util.UUID;

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

    public PacoteServico buscarPorId(UUID id) {
        return pacoteServicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PacoteServico não encontrado"));
    }

    public PacoteServico criar(PacoteServicoRequestDto dto, UUID pacoteId, UUID servicoId) {

        var pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado"));
        var servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Servico não encontrado"));

        PacoteServico pacoteServico = new PacoteServico();
        pacoteServico.setPacote(pacote);
        pacoteServico.setServico(servico);
        pacoteServico.setQuantidade(dto.getQuantidade());

        return pacoteServicoRepository.save(pacoteServico);
    }

    public PacoteServico atualizar(UUID id, PacoteServicoUpdateDto dto) {
        PacoteServico pacoteServico = buscarPorId(id);
        pacoteServico.setPacote(pacoteRepository.findById(dto.getPacoteId())
                .orElseThrow(() -> new RuntimeException("Pacote não encontrado")));
        pacoteServico.setServico(servicoRepository.findById(dto.getServicoId())
                .orElseThrow(() -> new RuntimeException("Servico não encontrado")));
        pacoteServico.setQuantidade(dto.getQuantidade());
        return pacoteServicoRepository.save(pacoteServico);
    }

    public void deletar(UUID id) {

        if (!pacoteServicoRepository.existsById(id)) {
            throw new RuntimeException("PacoteServico não encontrado");
        }

        pacoteServicoRepository.deleteById(id);
    }
    //restante das funções
}
