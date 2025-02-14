package prography.pingpong.domain.init.controller;

import static prography.pingpong.global.common.response.ResponseMessage.SUCCESS_RESPONSE;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong.domain.init.dto.request.InitRequest;
import prography.pingpong.domain.init.service.InitService;
import prography.pingpong.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class InitController {

    private final InitService initService;

    @PostMapping("/init")
    public ResponseEntity<ApiResponse<Void>> initData(@RequestBody InitRequest request) {
        initService.initializeData(request);
        return ResponseEntity.ok(ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage()));
    }
}
