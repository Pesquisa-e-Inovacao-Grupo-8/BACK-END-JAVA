package sptech.school.BACK_END_JAVA.comprovante.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.comprovante.entity.Comprovante;

import java.util.UUID;

public interface ComprovanteRepository extends JpaRepository<Comprovante, UUID> {
}


