package sptech.school.BACK_END_JAVA.servicoProfissional.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import sptech.school.BACK_END_JAVA.servicoProfissional.entity.ServicoProfissional;
import sptech.school.BACK_END_JAVA.servicoProfissional.repository.ServicoProfissionalRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ServicoProfissionalService {
    private final ServicoProfissionalRepository servicoProfissionalRepository;
    private final ServicoRepository servicoRepository;
    private final ProfissionalRepository profissionalRepository;

    public ServicoProfissionalService(ServicoProfissionalRepository servicoProfissionalRepository, ServicoRepository servicoRepository, ProfissionalRepository profissionalRepository) {
        this.servicoProfissionalRepository = servicoProfissionalRepository;
        this.servicoRepository = servicoRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<ServicoProfissional> listar() {return servicoProfissionalRepository.findAll();}

    public ServicoProfissional buscarPorId(UUID id) {
        return servicoProfissionalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço do profissional não encontrado"));
    }

    public ServicoProfissional criar(ServicoProfissional servicoProfissional, UUID servicoId, UUID profissionalId) {
        var servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        var profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        servicoProfissional.setServico(servico);
        servicoProfissional.setProfissional(profissional);

        return servicoProfissionalRepository.save(servicoProfissional);
    }

    public ServicoProfissional atualizar(UUID id, ServicoProfissional servicoProfissional) {
        if (!servicoProfissionalRepository.existsById(id)) {
            throw new RuntimeException("Serviço do profissional não encontrado");
        }
        servicoProfissional.setId(id);
        return servicoProfissionalRepository.save(servicoProfissional);
    }

    public void deletar(UUID id) {
        if (!servicoProfissionalRepository.existsById(id)) {
            throw new RuntimeException("Serviço do profissional não encontrado");
        }
        servicoProfissionalRepository.deleteById(id);
    }
}
