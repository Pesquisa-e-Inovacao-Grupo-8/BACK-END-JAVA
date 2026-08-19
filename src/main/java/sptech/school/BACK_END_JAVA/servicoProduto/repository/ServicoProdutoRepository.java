package sptech.school.BACK_END_JAVA.servicoProduto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.servicoProduto.entity.ServicoProduto;
import java.util.List;

public interface ServicoProdutoRepository extends JpaRepository<ServicoProduto, Integer> {
    List<ServicoProduto> findByServicoId(Integer servicoId);
}

