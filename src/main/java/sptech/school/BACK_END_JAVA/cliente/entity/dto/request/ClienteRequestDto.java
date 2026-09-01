package sptech.school.BACK_END_JAVA.cliente.entity.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class ClienteRequestDto {

    @Schema(description = "Representa notas de observações do cliente que são posteriormente acessadas (lidas) pelo profissional", example = "Tem sensibilidade a barulhos altos")
    private String observacoes;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do usuário", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID usuarioId;

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(UUID usuarioId) {
        this.usuarioId = usuarioId;
    }
}


