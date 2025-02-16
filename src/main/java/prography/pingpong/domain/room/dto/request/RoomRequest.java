package prography.pingpong.domain.room.dto.request;

import jakarta.validation.constraints.NotNull;

public record RoomRequest(
        @NotNull int userId
) {
}
