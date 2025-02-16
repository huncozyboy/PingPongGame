package prography.pingpong.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong.domain.exception.InvalidRequestException;
import prography.pingpong.domain.room.dto.request.RoomAttentionRequest;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.room.repository.RoomRepository;
import prography.pingpong.domain.user.service.UserGetService;
import prography.pingpong.domain.userRoom.entity.UserRoom;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;
import prography.pingpong.domain.userRoom.service.UserRoomService;

@Service
@RequiredArgsConstructor
public class RoomAttentionService {

    private final UserGetService userGetService;
    private final UserRoomService userRoomService;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    @Transactional
    public void attentionRoom(RoomAttentionRequest request, int roomId) {

        Room room = roomRepository.findByIdAndStatus(roomId, Room.Status.WAIT)
                .orElseThrow(InvalidRequestException::new);

        userGetService.findActiveUser(request.userId());

        if (userRoomService.isUserAlreadyInRoom(request.userId())) {
            throw new InvalidRequestException();
        }

        if (room.isFull(userRoomRepository)) {
            throw new InvalidRequestException();
        }

        UserRoom.Team assignedTeam = room.assignTeam(userRoomRepository);
        UserRoom userRoom = UserRoom.create(request.userId(), roomId, assignedTeam);
        userRoomRepository.save(userRoom);
    }
}
