package sptech.school.BACK_END_JAVA.servicoProfissional.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.profissional.repository.ProfissionalRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import sptech.school.BACK_END_JAVA.servicoProfissional.entity.ServicoProfissional;
import sptech.school.BACK_END_JAVA.servicoProfissional.repository.ServicoProfissionalRepository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

@Service
public class ServicoProfissionalService {

    private final ServicoProfissionalRepository repository;
    private final ServicoRepository servicoRepository;
    private final ProfissionalRepository profissionalRepository;

    public ServicoProfissionalService(
            ServicoProfissionalRepository repository,
            ServicoRepository servicoRepository,
            ProfissionalRepository profissionalRepository
    ) {
        this.repository = repository;
        this.servicoRepository = servicoRepository;
        this.profissionalRepository = profissionalRepository;
    }

    public List<ServicoProfissional> listar() {
        return repository.findAll();
    }

    public List<ServicoProfissional> listarPorProfissional(UUID profissionalId) {
        return repository.findByProfissional_Id(profissionalId);
    }

    @Transactional
    public void vincularServicos(UUID profissionalId, List<UUID> servicosIds) {

        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        repository.deleteByProfissional(profissional);

        if (servicosIds == null || servicosIds.isEmpty()) {
            return;
        }

        List<UUID> idsSemDuplicidade =
                new ArrayList<>(new LinkedHashSet<>(servicosIds));

        for (UUID servicoId : idsSemDuplicidade) {

            Servico servico = servicoRepository.findById(servicoId)
                    .orElseThrow(() -> new RuntimeException(
                            "Serviço não encontrado: " + servicoId
                    ));

            ServicoProfissional sp = new ServicoProfissional();
            sp.setProfissional(profissional);
            sp.setServico(servico);

            repository.save(sp);
        }
    }

    @Transactional
    public void remover(UUID profissionalId, UUID servicoId) {
        repository.deleteByProfissional_IdAndServico_Id(
                profissionalId,
                servicoId
        );
    }
}