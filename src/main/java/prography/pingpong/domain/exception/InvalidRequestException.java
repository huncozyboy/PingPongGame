package prography.pingpong.domain.exception;

import prography.pingpong.global.common.exception.BaseException;

public class InvalidRequestException extends BaseException {
    public InvalidRequestException() {
        super(ErrorMessage.INVALID_REQUEST.getCode(), ErrorMessage.INVALID_REQUEST.getMessage());
    }
}
