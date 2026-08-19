package sptech.school.BACK_END_JAVA.cliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.cliente.entity.Cliente;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByUsuario_Telefone(String telefone);

    Object findByUsuarioId(Integer clienteId);
}


