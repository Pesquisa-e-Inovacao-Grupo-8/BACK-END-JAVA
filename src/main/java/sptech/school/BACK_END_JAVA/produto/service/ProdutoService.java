package sptech.school.BACK_END_JAVA.produto.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.produto.entity.Produto;
import sptech.school.BACK_END_JAVA.produto.entity.dto.request.ProdutoRequestDto;
import sptech.school.BACK_END_JAVA.produto.repository.ProdutoRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listar() {
        return repository.findAll();
    }

    public Produto buscarPorId(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto criar(ProdutoRequestDto dto) {
        Produto produto = mapear(dto, new Produto());
        return repository.save(produto);
    }

    public Produto atualizar(UUID id, ProdutoRequestDto dto) {
        Produto produto = buscarPorId(id);
        mapear(dto, produto);
        return repository.save(produto);
    }

    private Produto mapear(ProdutoRequestDto dto, Produto produto) {
        produto.setNome(dto.getNome());
        produto.setUnidadeMedida(dto.getUnidadeMedida());
        produto.setCustoUnitario(dto.getCustoUnitario());
        return produto;
    }

    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado");
        }
        repository.deleteById(id);
    }
}