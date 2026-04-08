package sptech.school.BACK_END_JAVA.clientePacote.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ClientePacoteRequestDto {
    @NotNull
    @Schema(description = "Representa a chave estrangeira do cliente", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID clienteId;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do pacote", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID pacoteId;

    @NotNull
    @Schema(description = "Representa o estado do pacote para o cliente", example = "AT")
    private Boolean ativo;

    @NotNull
    @Schema(description = "Representa o momento da expiração do pacote para o cliente", example = "2026-12-31T23:59:59")
    private LocalDateTime dtExpiracao;

    @NotNull
    @Schema(description = "Representa a quantidade restantes do pacote para o cliente", example = "2")
    private Integer qtdUsos;
}
