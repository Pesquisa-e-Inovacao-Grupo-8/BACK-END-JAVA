package sptech.school.BACK_END_JAVA.servicoProduto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.servicoProduto.entity.ServicoProduto;
import java.util.List;
import java.util.UUID;

public interface ServicoProdutoRepository extends JpaRepository<ServicoProduto, UUID> {
    List<ServicoProduto> findByServicoId(UUID servicoId);
}