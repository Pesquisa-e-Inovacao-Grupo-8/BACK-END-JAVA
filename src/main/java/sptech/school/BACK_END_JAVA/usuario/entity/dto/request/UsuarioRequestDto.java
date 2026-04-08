package sptech.school.BACK_END_JAVA.usuario.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioRequestDto {
    @NotBlank
    @Schema(description = "Representa o nome do usuário", example = "Renata Tokutomi")
    private String nome;

    @NotBlank
    @Schema(description = "Representa o número de telefone do usuário", example = "+55 (11) 9999-999")
    private String telefone;

    @NotBlank
    @Schema(description = "Representa o número de cpf do usuário", example = "111.111.111-11")
    private String cpf;

    @NotBlank
    @Schema(description = "Representa a senha do usuário", example = "******")
    private String senha;

    @NotBlank
    @Schema(description = "Representa o email do usuário", example = "renata.tokutomi@email.com")
    private String email;

    @NotBlank
    @Schema(description = "Representa tipo de usuário no sistema", example = "ADM")
    private String tipo;

    @NotNull
    @Schema(description = "Representa o estado do usuário", example = "AT")
    private Boolean ativo;

    @NotBlank
    @Schema(description = "Representa o momento em que a conta é criada", example = "2026-12-31T23:59:59")
    private LocalDateTime criacao;

}
