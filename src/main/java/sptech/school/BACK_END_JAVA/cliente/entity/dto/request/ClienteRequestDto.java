package sptech.school.BACK_END_JAVA.cliente.entity.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


public class ClienteRequestDto {

    @Schema(description = "Representa notas de observaÃ§Ãµes do cliente que sÃ£o posteriormente acessadas (lidas) pelo profissional", example = "Tem sensibilidade a barulhos altos")
    private String observacoes;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do usuÃ¡rio", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer usuarioId;

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}


