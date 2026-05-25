package sptech.school.BACK_END_JAVA.pacote.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(Double precoTotal) {
        this.precoTotal = precoTotal;
    }
}
