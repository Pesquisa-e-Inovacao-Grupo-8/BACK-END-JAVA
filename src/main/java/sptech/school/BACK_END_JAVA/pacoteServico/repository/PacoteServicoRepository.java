package sptech.school.BACK_END_JAVA.pacoteServico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;
import sptech.school.BACK_END_JAVA.pacoteServico.entity.PacoteServico;

import java.util.List;

public interface PacoteServicoRepository
        extends JpaRepository<PacoteServico, Integer> {

    List<PacoteServico> findByPacote(Pacote pacote);
}

