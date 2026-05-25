package sptech.school.BACK_END_JAVA.agendamento.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class AgendamentoRequestDto {

    @NotNull
    @Schema(description = "Representa a data do agendamento", example = "2026-12-31")
    private LocalDate data;

    @NotNull
    @Schema(description = "Representa o horário em que o agendamento começa", example = "23:59:59")
    private LocalTime horaInicio;

    @NotNull
    @Schema(description = "Representa o horário em que o agendamento começa", example = "23:59:59")
    private LocalTime horaFim;

    @NotBlank
    @Schema(description = "Representa o status do agendamento", example = "AT")
    private String status;

    @NotBlank
    @Schema(description = "Representa o identificador da ordem do pedido", example = "J6GFbv0bh...")
    private String ordemPedido;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do cliente", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID clienteId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do profissional", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID profissionalId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do serviço", example = "b8e86162-d472-11e8-b36c-ccaf789d94a1")
    private UUID servicoId;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrdemPedido() {
        return ordemPedido;
    }

    public void setOrdemPedido(String ordemPedido) {
        this.ordemPedido = ordemPedido;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.clienteId = clienteId;
    }

    public UUID getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(UUID profissionalId) {
        this.profissionalId = profissionalId;
    }

    public UUID getServicoId() {
        return servicoId;
    }

    public void setServicoId(UUID servicoId) {
        this.servicoId = servicoId;
    }
}
