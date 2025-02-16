package prography.pingpong.domain.room.controller;

import static prography.pingpong.global.common.response.ResponseMessage.SUCCESS_RESPONSE;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prography.pingpong.domain.room.dto.request.RoomRequestDto.UserId;
import prography.pingpong.domain.room.service.TeamChangeService;
import prography.pingpong.global.common.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {

    private final TeamChangeService teamChangeService;

    @Operation(summary = "팀 변경")
    @PutMapping("/{roomId}")
    public ApiResponse<Void> changeTeam(@PathVariable int roomId, @RequestBody UserId request) {
        teamChangeService.teamChange(request, roomId);
        return ApiResponse.response(SUCCESS_RESPONSE.getCode(), SUCCESS_RESPONSE.getMessage());
    }
}
