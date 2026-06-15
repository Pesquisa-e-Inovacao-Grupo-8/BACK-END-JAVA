package sptech.school.BACK_END_JAVA.servicoProfissional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servicoProfissional.entity.ServicoProfissional;

import java.util.List;
import java.util.UUID;

public interface ServicoProfissionalRepository
        extends JpaRepository<ServicoProfissional, UUID> {

    boolean existsByProfissionalAndServico(
            Profissional profissional,
            Servico servico
    );

    List<ServicoProfissional> findByProfissional_Id(UUID profissionalId);

    void deleteByProfissional(Profissional profissional);

    void deleteByProfissional_IdAndServico_Id(
            UUID profissionalId,
            UUID servicoId
    );
}