package sptech.school.BACK_END_JAVA.pacote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.pacote.entity.Pacote;

import java.util.UUID;

public interface PacoteRepository extends JpaRepository<Pacote, UUID> {
}
