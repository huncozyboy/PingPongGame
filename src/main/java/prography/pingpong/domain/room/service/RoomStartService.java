package prography.pingpong.domain.room.service;

import java.util.Timer;
import java.util.TimerTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prography.pingpong.domain.exception.InvalidRequestException;
import prography.pingpong.domain.room.dto.request.RoomRequest;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.room.repository.RoomRepository;
import prography.pingpong.domain.user.service.UserGetService;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@Service
@RequiredArgsConstructor
public class RoomStartService {

    private final UserGetService userGetService;
    private final RoomRepository roomRepository;
    private final UserRoomRepository userRoomRepository;

    @Transactional
    public void startRoom(RoomRequest request, int roomId) {

        userGetService.findActiveUser(request.userId());

        Room room = roomRepository.findByIdAndStatus(roomId, Room.Status.WAIT)
                .orElseThrow(InvalidRequestException::new);

        if (!room.isFull(userRoomRepository)) {
            throw new InvalidRequestException();
        }

        if (!roomRepository.existsByIdAndHost(roomId, request.userId())) {
            throw new InvalidRequestException();
        }

        room.progress();
        roomRepository.save(room);
        scheduleRoomFinish(room);
    }

    private void scheduleRoomFinish(Room room) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                room.finish();
                roomRepository.save(room);
            }
        }, 60 * 1000);
    }
}
