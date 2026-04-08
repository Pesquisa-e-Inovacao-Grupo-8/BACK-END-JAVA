package sptech.school.BACK_END_JAVA.servicoProfissional.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ServicoProfissionalRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviço", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID servicoId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do profissional", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID profissionalId;
}
