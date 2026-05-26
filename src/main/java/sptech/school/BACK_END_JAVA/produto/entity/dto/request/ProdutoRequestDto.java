package sptech.school.BACK_END_JAVA.produto.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class ProdutoRequestDto {

    @NotBlank
    @Schema(description = "Nome do produto", example = "Shampoo Premium")
    private String nome;

    @NotBlank
    @Schema(description = "Unidade de medida do produto", example = "ml")
    private String unidadeMedida;

    @NotNull
    @PositiveOrZero
    @Schema(description = "Custo por unidade do produto", example = "0.25")
    private Double custoUnitario;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public Double getCustoUnitario() { return custoUnitario; }
    public void setCustoUnitario(Double custoUnitario) { this.custoUnitario = custoUnitario; }
}