package sptech.school.BACK_END_JAVA.produto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sptech.school.BACK_END_JAVA.produto.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}

