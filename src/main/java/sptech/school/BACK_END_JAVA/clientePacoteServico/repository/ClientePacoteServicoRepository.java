package sptech.school.BACK_END_JAVA.clientePacoteServico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.clientePacoteServico.entity.ClientePacoteServico;


public interface ClientePacoteServicoRepository
        extends JpaRepository<ClientePacoteServico, Integer> {
}

