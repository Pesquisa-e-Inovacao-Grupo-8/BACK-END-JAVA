package sptech.school.BACK_END_JAVA.agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;

import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
}
