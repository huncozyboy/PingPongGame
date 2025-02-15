package prography.pingpong.domain.room.dto.request;

import jakarta.validation.constraints.NotNull;

public record RoomCreateRequest(
        @NotNull int userId,

        @NotNull String roomType,

        @NotNull String title
) {
}
