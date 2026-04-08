package sptech.school.BACK_END_JAVA.agendamentoServico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.agendamentoServico.entity.AgendamentoServico;

import java.util.UUID;

public interface AgendamentoServicoRepository extends JpaRepository<AgendamentoServico, UUID> {
}
