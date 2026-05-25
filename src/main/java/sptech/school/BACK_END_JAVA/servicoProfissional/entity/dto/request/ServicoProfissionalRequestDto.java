package sptech.school.BACK_END_JAVA.servicoProfissional.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ServicoProfissionalRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviço", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID servicoId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do profissional", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID profissionalId;

    public UUID getServicoId() {
        return servicoId;
    }

    public void setServicoId(UUID servicoId) {
        this.servicoId = servicoId;
    }

    public UUID getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(UUID profissionalId) {
        this.profissionalId = profissionalId;
    }
}
