package sptech.school.BACK_END_JAVA.profissional.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

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
    private UUID usuarioId;

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}
