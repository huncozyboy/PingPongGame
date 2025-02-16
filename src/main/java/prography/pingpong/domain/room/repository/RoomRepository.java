package prography.pingpong.domain.room.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prography.pingpong.domain.room.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findByIdAndStatus(Integer roomId, Room.Status status);

    boolean existsByIdAndHost(Integer id, Integer host);
}
