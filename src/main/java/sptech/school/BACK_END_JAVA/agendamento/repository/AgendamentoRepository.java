package sptech.school.BACK_END_JAVA.agendamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import sptech.school.BACK_END_JAVA.agendamento.entity.Agendamento;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {
    List<Agendamento> findByData(LocalDate data);
    List<Agendamento> findAll();
    List<Agendamento> findByCliente_Usuario_Email(String email);
    List<Agendamento> findByProfissional_Usuario_Email(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Agendamento> findByProfissional_IdAndData(UUID profissionalId, LocalDate data);

    List<Agendamento> findByProfissional_IdAndDataOrderByHoraInicio(UUID profissionalId, LocalDate data);
}