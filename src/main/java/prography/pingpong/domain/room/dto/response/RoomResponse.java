package prography.pingpong.domain.room.dto.response;

import java.time.LocalDateTime;
import prography.pingpong.domain.room.entity.Room;

public record RoomResponse(
        int id,
        String title,
        int hostId,
        String roomType,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static RoomResponse from(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getTitle(),
                room.getHost(),
                room.getRoomType().name(),
                room.getStatus().name(),
                room.getCreateDate(),
                room.getUpdateDate()
        );
    }
}
