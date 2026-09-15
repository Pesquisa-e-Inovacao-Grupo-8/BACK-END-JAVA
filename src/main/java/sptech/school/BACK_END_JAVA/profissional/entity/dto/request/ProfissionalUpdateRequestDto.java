package sptech.school.BACK_END_JAVA.profissional.entity.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ProfissionalUpdateRequestDto {
    @NotBlank
    private String especialidade;
    private String descricao;
    private String foto;

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}