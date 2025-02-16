package prography.pingpong.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong.domain.exception.InvalidRequestException;
import prography.pingpong.domain.room.dto.request.RoomRequestDto;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.room.repository.RoomRepository;
import prography.pingpong.domain.user.service.UserGetService;
import prography.pingpong.domain.userRoom.entity.UserRoom;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;
import prography.pingpong.domain.userRoom.service.UserRoomService;

@Service
@RequiredArgsConstructor
public class RoomCreateService {

    private final UserGetService userGetService;
    private final UserRoomService userRoomService;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    @Transactional
    public void createRoom(RoomRequestDto.RoomCreate request) {

        userGetService.findActiveUser(request.userId());

        if (userRoomService.isUserAlreadyInRoom(request.userId())) {
            throw new InvalidRequestException();
        }

        Room room = Room.create(request.title(), request.userId(), request.roomType());
        roomRepository.save(room);

        UserRoom userRoom = UserRoom.create(request.userId(), room.getId(), UserRoom.Team.RED);
        userRoomRepository.save(userRoom);
    }
}
