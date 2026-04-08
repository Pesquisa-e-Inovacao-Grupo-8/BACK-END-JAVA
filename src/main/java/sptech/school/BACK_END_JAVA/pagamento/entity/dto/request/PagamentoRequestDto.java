package sptech.school.BACK_END_JAVA.pagamento.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PagamentoRequestDto {

    @NotNull
    @Schema(description = "Representa o valor a ser pago", example = "159.99")
    private Double valor;

    @NotBlank
    @Schema(description = "Representa a método cujo pagamento será efetuado", example = "Pix")
    private String metodo;

    @NotBlank
    @Schema(description = "Representa o estado do pagamento", example = "PE")
    private String status;

    @NotNull
    @Schema(description = "Representa o momento em que é gerado o pagamento", example = "2026-12-31T23:59:59")
    private LocalDateTime data;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do agendamento", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private Integer agendamentoId;

}
