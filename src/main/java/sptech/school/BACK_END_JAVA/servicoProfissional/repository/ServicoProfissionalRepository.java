package sptech.school.BACK_END_JAVA.servicoProfissional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.servicoProfissional.entity.ServicoProfissional;

import java.util.UUID;

public interface ServicoProfissionalRepository extends JpaRepository<ServicoProfissional, UUID> {
}
