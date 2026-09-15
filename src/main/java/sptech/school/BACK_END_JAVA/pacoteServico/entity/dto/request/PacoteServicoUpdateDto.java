package sptech.school.BACK_END_JAVA.pacoteServico.entity.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PacoteServicoUpdateDto {

    @NotNull
    private UUID pacoteId;

    @NotNull
    private UUID servicoId;
    @NotNull
    @Min(1)
    private Integer quantidade;

    public UUID getPacoteId() { return pacoteId; }
    public void setPacoteId(UUID pacoteId) { this.pacoteId = pacoteId; }
    public UUID getServicoId() { return servicoId; }
    public void setServicoId(UUID servicoId) { this.servicoId = servicoId; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
}