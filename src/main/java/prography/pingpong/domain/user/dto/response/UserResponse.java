package prography.pingpong.domain.user.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import prography.pingpong.domain.user.entity.User;

public record UserResponse(
        int id,
        int fakerId,
        String name,
        String email,
        String status,
        String createdAt,
        String updatedAt
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getFakerId(),
                user.getName(),
                user.getEmail(),
                user.getStatus().name(),
                formatDateTime(user.getCreatedAt()),
                formatDateTime(user.getUpdatedAt())
        );
    }

    private static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}
