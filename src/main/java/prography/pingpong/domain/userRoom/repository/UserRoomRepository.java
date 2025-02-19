package prography.pingpong.domain.userRoom.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prography.pingpong.domain.userRoom.entity.UserRoom;

@Repository
public interface UserRoomRepository extends JpaRepository<UserRoom, Integer> {

    boolean existsByUserId(Integer userId);

    int countByRoomId(Integer roomId);

    boolean existsByUserIdAndRoomId(Integer userId, Integer roomId);

    void deleteByRoomId(Integer roomId);

    void deleteByUserIdAndRoomId(Integer userId, Integer roomId);

    int countByRoomIdAndTeam(Integer roomId, UserRoom.Team team);

    Optional<UserRoom> findByUserIdAndRoomId(Integer userId, Integer roomId);
}
