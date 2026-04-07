package sptech.school.BACK_END_JAVA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import sptech.school.BACK_END_JAVA.entity.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, String> {
    Boolean existsByOrdemAgendamento(String ordemAgendamento);
    Agendamento findByOrdemAgendamento(String ordemAgendamento);


}