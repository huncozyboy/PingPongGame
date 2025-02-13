package prography.pingpong.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private Integer code;
    private String message;
    private T result;

    public static <T> ApiResponse<T> response(int code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }
}
