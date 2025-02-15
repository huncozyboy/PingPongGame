package prography.pingpong.domain.init.dto.request;

import jakarta.validation.constraints.NotNull;

public record InitRequest(
        @NotNull int seed,
        @NotNull int quantity
) {
}