package prography.pingpong.domain.init.dto.response;

import java.util.List;

public record FakerResponse(
        List<FakerUser> data
) {
}
