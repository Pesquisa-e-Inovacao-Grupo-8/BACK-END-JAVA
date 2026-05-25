package sptech.school.BACK_END_JAVA.comprovante.entity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class ComprovanteRequestDto {
    @NotBlank
    @Schema(description = "Representa o caminho url do comprovante de um pagamento efetuado", example = "https://api.infinitypay.io/v1/payments/tx_1234567890abcdef/receipt")
    private String url;

    @NotNull
    @Schema(description = "Representa a chave estrangeira do pagamento", example = "a9e86162-d472-11e8-b36c-ccaf789d94a0")
    private UUID pagamentoId;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public UUID getPagamentoId() {
        return pagamentoId;
    }

    public void setPagamentoId(UUID pagamentoId) {
        this.pagamentoId = pagamentoId;
    }
}
