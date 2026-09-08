package sptech.school.BACK_END_JAVA.profissionalHorario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sptech.school.BACK_END_JAVA.profissionalHorario.entity.ProfissionalHorario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfissionalHorarioRepository extends JpaRepository<ProfissionalHorario, UUID> {
    List<ProfissionalHorario> findByProfissional_IdOrderByDiaSemana(UUID profissionalId);
    Optional<ProfissionalHorario> findByProfissional_IdAndDiaSemana(UUID profissionalId, Integer diaSemana);
}
