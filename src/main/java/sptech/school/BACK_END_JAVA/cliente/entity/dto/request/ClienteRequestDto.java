package sptech.school.BACK_END_JAVA.cliente.entity.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClienteRequestDto {

    @Schema(description = "Representa notas de observações do cliente que são posteriormente acessadas (lidas) pelo profissional", example = "Tem sensibilidade a barulhos altos")
    private String observacoes;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do usuário", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID usuarioId;
}
