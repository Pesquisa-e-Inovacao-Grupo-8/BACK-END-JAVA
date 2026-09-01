package sptech.school.BACK_END_JAVA.servicoProduto.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public class ServicoProdutoRequestDto {

    @NotNull
    @Schema(description = "ID do Serviço")
    private UUID servicoId;

    @NotNull
    @Schema(description = "ID do Produto")
    private UUID produtoId;

    @NotNull
    @Positive
    @Schema(description = "Quantidade usada do produto neste serviço", example = "50.0")
    private Double quantidadeUsada;

    public UUID getServicoId() { return servicoId; }
    public void setServicoId(UUID servicoId) { this.servicoId = servicoId; }

    public UUID getProdutoId() { return produtoId; }
    public void setProdutoId(UUID produtoId) { this.produtoId = produtoId; }

    public Double getQuantidadeUsada() { return quantidadeUsada; }
    public void setQuantidadeUsada(Double quantidadeUsada) { this.quantidadeUsada = quantidadeUsada; }
}

