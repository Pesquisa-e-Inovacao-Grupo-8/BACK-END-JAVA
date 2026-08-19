package sptech.school.BACK_END_JAVA.agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;

import java.time.LocalDate;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {
    List<Agendamento> findByData(LocalDate data);
    List<Agendamento> findAll();
}

