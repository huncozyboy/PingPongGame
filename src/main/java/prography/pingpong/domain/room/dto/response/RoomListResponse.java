package prography.pingpong.domain.room.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record RoomListResponse(
        int totalElements,
        int totalPages,
        List<RoomResponse> roomList
) {
    public static RoomListResponse of(Page<RoomResponse> page) {
        return new RoomListResponse(
                (int) page.getTotalElements(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
