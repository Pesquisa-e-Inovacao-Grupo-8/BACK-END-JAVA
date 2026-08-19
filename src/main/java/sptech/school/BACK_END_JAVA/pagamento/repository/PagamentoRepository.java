package sptech.school.BACK_END_JAVA.pagamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.pagamento.entity.Pagamento;


public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}


