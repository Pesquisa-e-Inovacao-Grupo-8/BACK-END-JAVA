package sptech.school.BACK_END_JAVA.servicoProfissional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servicoProfissional.entity.ServicoProfissional;

import java.util.List;

public interface ServicoProfissionalRepository
        extends JpaRepository<ServicoProfissional, Integer> {

    boolean existsByProfissionalAndServico(
            Profissional profissional,
            Servico servico
    );

    List<ServicoProfissional> findByProfissional_Id(Integer profissionalId);

    void deleteByProfissional(Profissional profissional);

    void deleteByProfissional_IdAndServico_Id(
            Integer profissionalId,
            Integer servicoId
    );
}

