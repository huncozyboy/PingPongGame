package prography.pingpong.domain.room.controller;

import static prography.pingpong.global.common.response.ResponseMessage.SUCCESS_RESPONSE;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong.domain.room.dto.request.RoomCreateRequest;
import prography.pingpong.domain.room.dto.response.RoomListResponse;
import prography.pingpong.domain.room.dto.response.RoomResponse;
import prography.pingpong.domain.room.service.RoomCreateService;
import prography.pingpong.domain.room.service.RoomGetService;
import prography.pingpong.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomCreateService roomCreateService;
    private final RoomGetService roomGetService;

    @Operation(summary = "방 생성")
    @PostMapping("/room")
    public ApiResponse<Void> createRooms(@RequestBody RoomCreateRequest request) {
        roomCreateService.createRoom(request);
        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage());
    }

    @Operation(summary = "모든 방 조회")
    @GetMapping("/rooms")
    public ApiResponse<RoomListResponse> getAllRooms(@RequestParam int page, @RequestParam int size) {
        Page<RoomResponse> rooms = roomGetService.getAllRooms(page, size);
        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage(),
                RoomListResponse.of(rooms));
    }
}
