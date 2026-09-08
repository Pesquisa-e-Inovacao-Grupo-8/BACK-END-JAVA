package sptech.school.BACK_END_JAVA.clientePacote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.clientePacote.entity.ClientePacote;

import java.util.UUID;
import java.util.List;

public interface ClientePacoteRepository extends JpaRepository<ClientePacote, UUID> {
	List<ClientePacote> findByCliente_Usuario_Id(UUID usuarioId);
}
