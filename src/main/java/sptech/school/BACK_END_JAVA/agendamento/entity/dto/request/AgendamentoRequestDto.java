package sptech.school.BACK_END_JAVA.agendamento.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
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
}
