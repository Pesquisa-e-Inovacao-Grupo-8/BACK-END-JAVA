package sptech.school.BACK_END_JAVA.pacoteServico.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class PacoteServicoRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do pacote", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer pacoteId;
    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviÃ§o", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer servicoId;
    @NotNull
    @Schema(description = "Representa o valor do preÃ§o do serviÃ§o ao ser inserido no pacote", example = "15.99")
    private Double preco;

    public Integer getPacoteId() {
        return pacoteId;
    }

    public void setPacoteId(Integer pacoteId) {
        this.pacoteId = pacoteId;
    }

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}


