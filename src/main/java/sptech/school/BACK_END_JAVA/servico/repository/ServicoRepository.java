package sptech.school.BACK_END_JAVA.servico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;

import java.util.UUID;

public interface ServicoRepository extends JpaRepository<Servico, UUID> {
}
