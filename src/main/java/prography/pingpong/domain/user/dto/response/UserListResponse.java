package prography.pingpong.domain.user.dto.response;

import java.util.List;
import org.springframework.data.domain.Page;

public record UserListResponse(
        int totalElements,
        int totalPages,
        List<UserResponse> userList
) {
    public static UserListResponse of(Page<UserResponse> page) {
        return new UserListResponse(
                (int) page.getTotalElements(),
                page.getTotalPages(),
                page.getContent()
        );
    }
}
