package prography.pingpong.domain.room.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong.domain.exception.InvalidRequestException;
import prography.pingpong.domain.room.dto.request.RoomRequestDto.UserId;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.room.repository.RoomRepository;
import prography.pingpong.domain.user.service.UserGetService;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@Service
@RequiredArgsConstructor
public class RoomOutService {

    private final UserGetService userGetService;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    @Transactional
    public void outRoom(UserId request, int roomId) {

        userGetService.findActiveUser(request.userId());

        Room room = roomRepository.findById(roomId)
                .orElseThrow(InvalidRequestException::new);

        if (!userRoomRepository.existsByUserIdAndRoomId(request.userId(), roomId)) {
            throw new InvalidRequestException();
        }

        if (room.isInProgressOrFinished()) {
            throw new InvalidRequestException();
        }

        if (room.getHost().equals(request.userId())) {
            userRoomRepository.deleteByRoomId(roomId);
            room.finish();
            roomRepository.save(room);
            return;
        }

        userRoomRepository.deleteByUserIdAndRoomId(request.userId(), roomId);

        if (userRoomRepository.countByRoomId(roomId) == 0) {
            room.finish();
            roomRepository.save(room);
        }
    }
}
