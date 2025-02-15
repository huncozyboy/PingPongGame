package prography.pingpong.domain.room.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import prography.pingpong.domain.exception.InvalidRequestException;
import prography.pingpong.domain.room.dto.response.RoomDetailResponse;
import prography.pingpong.domain.room.dto.response.RoomResponse;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.room.repository.RoomRepository;

@Service
@RequiredArgsConstructor
public class RoomGetService {

    private final RoomRepository roomRepository;

    @Transactional
    public Page<RoomResponse> getAllRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Room> rooms = roomRepository.findAll(pageable);

        return rooms.map(RoomResponse::from);
    }

    @Transactional
    public RoomDetailResponse getRoomDetail(int roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(InvalidRequestException::new);

        return RoomDetailResponse.from(room);
    }
}
