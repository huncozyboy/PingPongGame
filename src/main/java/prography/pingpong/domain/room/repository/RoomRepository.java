package prography.pingpong.domain.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prography.pingpong.domain.room.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}
