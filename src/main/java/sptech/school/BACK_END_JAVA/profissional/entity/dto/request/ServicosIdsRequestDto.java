package sptech.school.BACK_END_JAVA.profissional.entity.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public class ServicosIdsRequestDto {
    @NotNull
    private final List<UUID> servicosIds;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ServicosIdsRequestDto(List<UUID> servicosIds) {
        this.servicosIds = servicosIds;
    }

    public List<UUID> getServicosIds() { return servicosIds; }
}