package sptech.school.BACK_END_JAVA.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.usuario.entity.Usuario;

import java.util.UUID;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
