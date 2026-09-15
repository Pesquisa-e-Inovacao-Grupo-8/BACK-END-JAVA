package sptech.school.BACK_END_JAVA.clientePacote.entity.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class ClientePacoteUpdateDto {

    @NotNull
    private Boolean ativo;

    @NotNull
    private LocalDateTime dtExpiracao;

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getDtExpiracao() { return dtExpiracao; }
    public void setDtExpiracao(LocalDateTime dtExpiracao) { this.dtExpiracao = dtExpiracao; }
}