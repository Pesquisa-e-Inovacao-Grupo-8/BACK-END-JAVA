package sptech.school.BACK_END_JAVA.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;

import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
}
