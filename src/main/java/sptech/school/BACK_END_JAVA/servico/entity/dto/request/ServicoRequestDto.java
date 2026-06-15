package sptech.school.BACK_END_JAVA.servico.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ServicoRequestDto {
    @NotBlank
    @Schema(description = "Representa o nome do serviço", example = "Pedicure")
    private String nome;

    @NotBlank
    @Positive
    @Schema(description = "Representa a duração em minutos do serviço prestado", example = "120")
    private Integer duracaoMinutos;

    @NotBlank
    @Schema(description = "Representa a descrição do serviço", example = "Oferece cuidados aos pés")
    private String descricao;

    @NotNull
    @Schema(description = "Representa o preço do serviço", example = "59.99")
    private Double preco;

    @NotBlank
    @Schema(description = "Representa o estado do serviço", example = "AT")
    private Boolean ativo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
