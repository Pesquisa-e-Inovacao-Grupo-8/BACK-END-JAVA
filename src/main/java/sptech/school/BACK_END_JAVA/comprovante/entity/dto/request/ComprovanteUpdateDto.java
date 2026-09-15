package sptech.school.BACK_END_JAVA.comprovante.entity.dto.request;

import jakarta.validation.constraints.NotBlank;

public class ComprovanteUpdateDto {

    @NotBlank
    private String url;

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}