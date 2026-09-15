package sptech.school.BACK_END_JAVA.usuario.entity.dto.request;

import jakarta.validation.constraints.NotBlank;

public class MeusDadosUpdateDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String telefone;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}