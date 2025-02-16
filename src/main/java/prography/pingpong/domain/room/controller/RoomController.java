package prography.pingpong.domain.room.controller;

import static prography.pingpong.global.common.response.ResponseMessage.SUCCESS_RESPONSE;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong.domain.room.dto.request.RoomAttentionRequest;
import prography.pingpong.domain.room.dto.request.RoomCreateRequest;
import prography.pingpong.domain.room.dto.response.RoomDetailResponse;
import prography.pingpong.domain.room.dto.response.RoomListResponse;
import prography.pingpong.domain.room.dto.response.RoomResponse;
import prography.pingpong.domain.room.service.RoomAttentionService;
import prography.pingpong.domain.room.service.RoomCreateService;
import prography.pingpong.domain.room.service.RoomGetService;
import prography.pingpong.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomCreateService roomCreateService;
    private final RoomGetService roomGetService;
    private final RoomAttentionService roomAttentionService;

    @Operation(summary = "방 생성")
    @PostMapping
    public ApiResponse<Void> createRooms(@RequestBody RoomCreateRequest request) {
        roomCreateService.createRoom(request);
        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage());
    }

    @Operation(summary = "모든 방 조회")
    @GetMapping
    public ApiResponse<RoomListResponse> getAllRooms(@RequestParam int page, @RequestParam int size) {
        Page<RoomResponse> rooms = roomGetService.getAllRooms(page, size);
        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage(),
                RoomListResponse.of(rooms));
    }

    @Operation(summary = "방 상세 조회")
    @GetMapping("/{roomId}")
    public ApiResponse<RoomDetailResponse> getRoomDetail(@PathVariable int roomId) {
        RoomDetailResponse roomDetail = roomGetService.getRoomDetail(roomId);
        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage(), roomDetail);
    }

    @Operation(summary = "방 참가")
    @PostMapping("/attention/{roomId}")
    public ApiResponse<Void> attentionRoom(@PathVariable int roomId, @RequestBody RoomAttentionRequest request) {
        roomAttentionService.attentionRoom(request, roomId);
        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage());
    }
}
