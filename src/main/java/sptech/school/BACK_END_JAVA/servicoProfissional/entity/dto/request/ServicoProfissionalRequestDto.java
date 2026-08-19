package sptech.school.BACK_END_JAVA.servicoProfissional.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public class ServicoProfissionalRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviÃ§o", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer servicoId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do profissional", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer profissionalId;

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public Integer getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Integer profissionalId) {
        this.profissionalId = profissionalId;
    }
}


