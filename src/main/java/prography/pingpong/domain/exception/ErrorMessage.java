package prography.pingpong.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {

    INVALID_REQUEST(201, "잘못된 요청입니다.");

    private final int code;
    private final String message;
}
