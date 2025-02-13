package prography.pingpong.global.common.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong.global.common.response.ApiResponse;
import prography.pingpong.global.common.response.ResponseMessage;

@RestController
public class StatusCheckController {

    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<Void>> checkHealthStatus() {
        return ResponseEntity.ok(
                ApiResponse.response(
                        ResponseMessage.SUCCESS_RESPONSE.getCode(),
                        ResponseMessage.SUCCESS_RESPONSE.getMessage()
                )
        );
    }
}
