package prography.pingpong.domain.user.controller;

import static prography.pingpong.global.common.response.ResponseMessage.SUCCESS_RESPONSE;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong.domain.user.dto.response.UserListResponse;
import prography.pingpong.domain.user.dto.response.UserResponse;
import prography.pingpong.domain.user.service.UserGetService;
import prography.pingpong.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserGetService userGetService;

    @Operation(summary = "유저 전체 조회")
    @GetMapping("/users")
    public ApiResponse<UserListResponse> getAllUsers(@RequestParam int page, @RequestParam int size) {
        Page<UserResponse> users = userGetService.getAllUsers(page, size);

        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage(),
                UserListResponse.of(users));
    }
}
