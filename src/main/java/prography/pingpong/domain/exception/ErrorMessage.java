package prography.pingpong.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    INVALID_REQUEST(201, "불가능한 요청입니다.");

    private final int code;
    private final String message;
}
