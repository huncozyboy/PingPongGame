package prography.pingpong.domain.userRoom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer roomId;

    @Column(nullable = false)
    private Integer userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Team team;

    public enum Team {
        RED, BLUE
    }

    public static UserRoom create(Integer userId, Integer roomId, Team team) {
        return UserRoom.builder()
                .userId(userId)
                .roomId(roomId)
                .team(team)
                .build();
    }

    public static Team getOppositeTeam(Team currentTeam) {
        return currentTeam == Team.RED ? Team.BLUE : Team.RED;
    }

    public static boolean isTeamFull(UserRoomRepository userRoomRepository, Integer roomId, Team team,
                                     Room.RoomType roomType) {
        int currentTeamCount = userRoomRepository.countByRoomIdAndTeam(roomId, team);
        int maxTeamSize = roomType == Room.RoomType.SINGLE ? 1 : 2;
        return currentTeamCount >= maxTeamSize;
    }

    public void changeTeam(Team newTeam) {
        this.team = newTeam;
    }
}
