package sptech.school.BACK_END_JAVA.pacoteServico.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class PacoteServicoRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do pacote", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID pacoteId;
    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviço", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID servicoId;
    @NotNull
    @Schema(description = "Representa o valor do preço do serviço ao ser inserido no pacote", example = "15.99")
    private Double preco;

    public UUID getPacoteId() {
        return pacoteId;
    }

    public void setPacoteId(UUID pacoteId) {
        this.pacoteId = pacoteId;
    }

    public UUID getServicoId() {
        return servicoId;
    }

    public void setServicoId(UUID servicoId) {
        this.servicoId = servicoId;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
