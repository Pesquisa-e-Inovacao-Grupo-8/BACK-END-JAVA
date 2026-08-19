package sptech.school.BACK_END_JAVA.agendamentoServico.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class AgendamentoServicoRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do agendamento", example = "1")
    private Integer agendamentoId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviço", example = "1")
    private Integer servicoId;

    public Integer getAgendamentoId() {
        return agendamentoId;
    }

    public void setAgendamentoId(Integer agendamentoId) {
        this.agendamentoId = agendamentoId;
    }

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }
}
