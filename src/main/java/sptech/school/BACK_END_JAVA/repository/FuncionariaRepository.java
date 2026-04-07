package sptech.school.BACK_END_JAVA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.entity.Funcionaria;

public interface FuncionariaRepository extends JpaRepository<Funcionaria, String>{
}