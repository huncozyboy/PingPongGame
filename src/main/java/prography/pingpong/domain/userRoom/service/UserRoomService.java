package prography.pingpong.domain.userRoom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@Service
@RequiredArgsConstructor
public class UserRoomService {

    private final UserRoomRepository userRoomRepository;

    public boolean isUserAlreadyInRoom(Integer userId) {
        return userRoomRepository.existsByUserId(userId);
    }
}
