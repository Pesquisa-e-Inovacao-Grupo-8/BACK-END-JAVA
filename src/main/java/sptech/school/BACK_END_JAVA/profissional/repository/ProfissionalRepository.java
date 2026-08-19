package sptech.school.BACK_END_JAVA.profissional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.profissional.entity.Profissional;

import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Integer> {
    Optional<Profissional> findByUsuarioId(Integer usuarioId);
}


