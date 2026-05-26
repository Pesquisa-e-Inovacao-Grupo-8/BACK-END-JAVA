package sptech.school.BACK_END_JAVA.servicoProduto.service;

import org.springframework.stereotype.Service;
import sptech.school.BACK_END_JAVA.produto.entity.Produto;
import sptech.school.BACK_END_JAVA.produto.repository.ProdutoRepository;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import sptech.school.BACK_END_JAVA.servico.repository.ServicoRepository;
import sptech.school.BACK_END_JAVA.servicoProduto.entity.ServicoProduto;
import sptech.school.BACK_END_JAVA.servicoProduto.repository.ServicoProdutoRepository;

import java.util.List;
import java.util.UUID;

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

    public List<ServicoProduto> listarPorServico(UUID servicoId) {
        return servicoProdutoRepository.findByServicoId(servicoId);
    }

    public ServicoProduto criar(UUID servicoId, UUID produtoId, Double quantidadeUsada) {
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ServicoProduto sp = new ServicoProduto();
        sp.setServico(servico);
        sp.setProduto(produto);
        sp.setQuantidadeUsada(quantidadeUsada);

        return servicoProdutoRepository.save(sp);
    }

    public void deletar(UUID id) {
        if (!servicoProdutoRepository.existsById(id)) {
            throw new RuntimeException("Vínculo não encontrado");
        }
        servicoProdutoRepository.deleteById(id);
    }
}