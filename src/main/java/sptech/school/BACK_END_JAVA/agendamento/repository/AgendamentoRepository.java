package sptech.school.BACK_END_JAVA.agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    List<Agendamento> findByData(LocalDate data);
    List<Agendamento> findAll();
}

