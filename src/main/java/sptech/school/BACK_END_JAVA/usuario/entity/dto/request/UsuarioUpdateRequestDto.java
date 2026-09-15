package sptech.school.BACK_END_JAVA.usuario.entity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioUpdateRequestDto {

    @NotBlank
    private String nome;

    @NotBlank
    private String telefone;

    @NotBlank
    private String cpf;

    private String senha;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String tipo;

    @NotNull
    private Boolean ativo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
}