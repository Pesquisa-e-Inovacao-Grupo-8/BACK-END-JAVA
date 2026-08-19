package sptech.school.BACK_END_JAVA.produto.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.produto.entity.Produto;
import sptech.school.BACK_END_JAVA.produto.repository.ProdutoRepository;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto criar(Produto produto) {
        return repository.save(produto);
    }

    public Produto atualizar(Integer id, Produto produto) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        produto.setId(id);
        return repository.save(produto);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }
}