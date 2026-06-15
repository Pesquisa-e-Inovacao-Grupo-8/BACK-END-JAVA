package sptech.school.BACK_END_JAVA.agendamento.entity.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AgendamentoRequestDto {

    private UUID clienteId;

    private String nomeClienteAvulso;

    private String telefoneClienteAvulso;

    private String status;

    private LocalDate data;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private UUID profissionalId;

    private UUID servicoId;

    private List<UUID> servicos;
}