package sptech.school.BACK_END_JAVA.servicoProduto.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ServicoProdutoRequestDto {

    @NotNull
    @Schema(description = "ID do ServiÃ§o")
    private Integer servicoId;

    @NotNull
    @Schema(description = "ID do Produto")
    private Integer produtoId;

    @NotNull
    @Positive
    @Schema(description = "Quantidade usada do produto neste serviÃ§o", example = "50.0")
    private Double quantidadeUsada;

    public Integer getServicoId() { return servicoId; }
    public void setServicoId(Integer servicoId) { this.servicoId = servicoId; }

    public Integer getProdutoId() { return produtoId; }
    public void setProdutoId(Integer produtoId) { this.produtoId = produtoId; }

    public Double getQuantidadeUsada() { return quantidadeUsada; }
    public void setQuantidadeUsada(Double quantidadeUsada) { this.quantidadeUsada = quantidadeUsada; }
}

