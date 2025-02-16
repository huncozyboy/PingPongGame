package prography.pingpong.domain.room.dto.request;

import jakarta.validation.constraints.NotNull;

public record RoomOutRequest(
        @NotNull int userId
) {
}
