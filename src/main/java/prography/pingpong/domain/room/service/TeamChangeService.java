package prography.pingpong.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong.domain.exception.InvalidRequestException;
import prography.pingpong.domain.room.dto.request.RoomRequest;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.room.repository.RoomRepository;
import prography.pingpong.domain.user.service.UserGetService;
import prography.pingpong.domain.userRoom.entity.UserRoom;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@Service
@RequiredArgsConstructor
public class TeamChangeService {

    private final UserGetService userGetService;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    @Transactional
    public void teamChange(RoomRequest request, int roomId) {

        userGetService.findActiveUser(request.userId());

        Room room = roomRepository.findByIdAndStatus(roomId, Room.Status.WAIT)
                .orElseThrow(InvalidRequestException::new);

        UserRoom userRoom = userRoomRepository.findByUserIdAndRoomId(request.userId(), roomId)
                .orElseThrow(InvalidRequestException::new);

        UserRoom.Team newTeam = UserRoom.getOppositeTeam(userRoom.getTeam());

        if (UserRoom.isTeamFull(userRoomRepository, roomId, newTeam, room.getRoomType())) {
            throw new InvalidRequestException();
        }

        userRoom.changeTeam(newTeam);
        userRoomRepository.save(userRoom);
    }
}
