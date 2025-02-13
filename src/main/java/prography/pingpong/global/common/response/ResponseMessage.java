package prography.pingpong.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    
    SUCCESS_RESPONSE(200, "API 요청이 성공했습니다.");

    private final int code;
    private final String message;
}
