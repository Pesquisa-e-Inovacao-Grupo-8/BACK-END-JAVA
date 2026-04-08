package sptech.school.BACK_END_JAVA.pacote.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PacoteRequestDto {
    @NotBlank
    @Schema(description = "Representa o nome do pacote", example = "pacote de verão")
    private String nome;

    @NotBlank
    @Schema(description = "Representa a descrição do pacote", example = "Contém: Unhas, Cabelo e Massagem")
    private String descricao;

    @NotNull
    @Schema(description = "Representa o preço total do pacote", example = "150.00")
    private Double precoTotal;
}
