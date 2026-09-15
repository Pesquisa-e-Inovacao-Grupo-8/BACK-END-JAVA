package sptech.school.BACK_END_JAVA.clientePacoteServico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.clientePacoteServico.entity.ClientePacoteServico;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;

import java.util.List;
import java.util.UUID;

public interface ClientePacoteServicoRepository
        extends JpaRepository<ClientePacoteServico, UUID> {
        List<ClientePacoteServico> findByClientePacote(ClientePacote clientePacote);
}