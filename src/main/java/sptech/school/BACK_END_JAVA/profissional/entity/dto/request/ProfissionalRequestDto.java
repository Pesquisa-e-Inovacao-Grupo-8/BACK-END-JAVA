package sptech.school.BACK_END_JAVA.profissional.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfissionalRequestDto {

    @NotBlank
    @Schema(description = "Representa a especialidade do profissional", example = "Cabelo")
    private String especialidade;

    @Schema(description = "Representa algumas características importantes do profissional", example = "Trabalhando com pedicure a 5 anos")
    private String descricao;

    @Schema(description = "Representa a imagem que o profissional terá vinculada a sua conta", example = "")
    private String foto;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do usuário", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer usuarioId;
}
