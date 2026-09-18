package sptech.school.BACK_END_JAVA.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
	Optional<Pagamento> findByAgendamento_Id(UUID agendamentoId);
}
