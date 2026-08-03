package sptech.school.BACK_END_JAVA.servicoProduto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import sptech.school.BACK_END_JAVA.produto.entity.Produto;
import sptech.school.BACK_END_JAVA.servico.entity.Servico;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "servico_produto")
public class ServicoProduto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "fk_servico", nullable = false)
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "fk_produto", nullable = false)
    private Produto produto;

    @Column(name = "quantidade_usada", nullable = false)
    private Double quantidadeUsada;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Servico getServico() { return servico; }
    public void setServico(Servico servico) { this.servico = servico; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public Double getQuantidadeUsada() { return quantidadeUsada; }
    public void setQuantidadeUsada(Double quantidadeUsada) { this.quantidadeUsada = quantidadeUsada; }
}