package sptech.school.BACK_END_JAVA.agendamentoServico.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AgendamentoServicoRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do agendamento", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID agendamentoId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviço", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID servicoId;

    public UUID getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(UUID agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public UUID getServicoId() {
        return servicoId;
    }

    public void setServicoId(UUID servicoId) {
        this.servicoId = servicoId;
    }
}
