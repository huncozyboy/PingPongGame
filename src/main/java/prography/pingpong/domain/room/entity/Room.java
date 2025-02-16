package prography.pingpong.domain.room.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prography.pingpong.domain.userRoom.entity.UserRoom;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer host;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updateDate;

    public enum RoomType {
        SINGLE, DOUBLE
    }

    public enum Status {
        WAIT,
        PROGRESS,
        FINISH
    }

    public static Room create(String title, Integer host, String roomType) {
        return Room.builder()
                .title(title)
                .host(host)
                .roomType(RoomType.valueOf(roomType.toUpperCase()))
                .status(Status.WAIT)
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public boolean isFull(UserRoomRepository userRoomRepository) {
        int currentCount = userRoomRepository.countByRoomId(this.id);
        return (this.roomType == RoomType.SINGLE && currentCount >= 2)
                || (this.roomType == RoomType.DOUBLE && currentCount >= 4);
    }

    public UserRoom.Team assignTeam(UserRoomRepository userRoomRepository) {
        int currentCount = userRoomRepository.countByRoomId(this.id);

        if (this.roomType == RoomType.SINGLE) {
            return UserRoom.Team.BLUE;
        }

        return currentCount == 1 ? UserRoom.Team.RED : UserRoom.Team.BLUE;
    }

    public void finish() {
        this.status = Status.FINISH;
        this.updateDate = LocalDateTime.now();
    }

    public void progress() {
        this.status = Status.PROGRESS;
        this.updateDate = LocalDateTime.now();
    }
}
