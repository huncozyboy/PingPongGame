package prography.pingpong.domain.room.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RoomRequestDto {

    public record RoomCreate(
            @NotNull int userId,
            @NotBlank String title,
            @NotNull String roomType
    ) {
    }

    public record UserId(
            @NotNull int userId
    ) {
    }
}
