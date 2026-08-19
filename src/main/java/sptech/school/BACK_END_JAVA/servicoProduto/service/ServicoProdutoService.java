package sptech.school.BACK_END_JAVA.servicoProduto.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.produto.entity.Produto;
import sptech.school.BACK_END_JAVA.produto.repository.ProdutoRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import sptech.school.BACK_END_JAVA.servicoProduto.entity.ServicoProduto;
import sptech.school.BACK_END_JAVA.servicoProduto.repository.ServicoProdutoRepository;

import java.util.List;

@Service
public class ServicoProdutoService {

    private final ServicoProdutoRepository servicoProdutoRepository;
    private final ServicoRepository servicoRepository;
    private final ProdutoRepository produtoRepository;

    public ServicoProdutoService(ServicoProdutoRepository servicoProdutoRepository,
                                 ServicoRepository servicoRepository,
                                 ProdutoRepository produtoRepository) {
        this.servicoProdutoRepository = servicoProdutoRepository;
        this.servicoRepository = servicoRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<ServicoProduto> listar() {
        return servicoProdutoRepository.findAll();
    }

    public List<ServicoProduto> listarPorServico(Integer servicoId) {
        return servicoProdutoRepository.findByServicoId(servicoId);
    }

    public ServicoProduto criar(Integer servicoId, Integer produtoId, Double quantidadeUsada) {
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("ServiÃ§o nÃ£o encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto nÃ£o encontrado"));

        ServicoProduto sp = new ServicoProduto();
        sp.setServico(servico);
        sp.setProduto(produto);
        sp.setQuantidadeUsada(quantidadeUsada);

        return servicoProdutoRepository.save(sp);
    }

    public void deletar(Integer id) {
        if (!servicoProdutoRepository.existsById(id)) {
            throw new RuntimeException("VÃ­nculo nÃ£o encontrado");
        }
        servicoProdutoRepository.deleteById(id);
    }
}

