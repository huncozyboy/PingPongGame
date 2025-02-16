package prography.pingpong.domain.init.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record FakerResponse(
        List<FakerUser> data
) {
}
