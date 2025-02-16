package prography.pingpong;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import prography.pingpong.domain.room.entity.Room;
import prography.pingpong.domain.room.entity.Room.RoomType;
import prography.pingpong.domain.room.repository.RoomRepository;
import prography.pingpong.domain.user.entity.User;
import prography.pingpong.domain.user.repository.UserRepository;
import prography.pingpong.domain.userRoom.entity.UserRoom;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @SpyBean
    private UserRepository userRepository;
    @SpyBean
    private RoomRepository roomRepository;
    @SpyBean
    private UserRoomRepository userRoomRepository;

    @BeforeEach
    void setup() {
        userRoomRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();

        System.out.println("Database cleared.");
    }

    @Test
    @DisplayName("헬스체크 API 테스트")
    void healthCheckTest() throws Exception {
        mockMvc.perform(get("/health-check"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("API 요청이 성공했습니다."));
    }

    @Test
    @DisplayName("방 생성 API 테스트")
    void createRoomTest() throws Exception {
        User user = User.createUser(1, "testUser1", "test1@example.com");
        userRepository.save(user);

        String requestBody = """
                {
                    "userId": 1,
                    "roomType": "SINGLE",
                    "title": "Test Room"
                }
                """;

        mockMvc.perform(post("/room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("API 요청이 성공했습니다."));
    }

    @Test
    @DisplayName("방 전체 조회 API 테스트")
    void getAllRoomsTest() throws Exception {
        mockMvc.perform(get("/room?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.result").isNotEmpty());
    }

    @Test
    @DisplayName("방 상세 조회 API 테스트")
    void getRoomDetailTest() throws Exception {
        User user = User.createUser(1, "testUser1", "test1@example.com");
        userRepository.save(user);

        Room room = Room.create("Test Room", user.getId(), RoomType.SINGLE.name());
        roomRepository.save(room);

        mockMvc.perform(get("/room/" + room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.result.id").value(room.getId()));
    }

    @Test
    @DisplayName("방 참가 API 테스트")
    void attentionRoomTest() throws Exception {

        User user1 = User.createUser(1, "testUser1", "test1@example.com");
        userRepository.save(user1);

        User user2 = User.createUser(2, "testUser2", "test2@example.com");
        userRepository.save(user2);

        Room room = Room.create("Test Room", user1.getId(), RoomType.SINGLE.name());
        roomRepository.save(room);

        String requestBody = """
                {
                    "userId": 2
                }
                """;

        mockMvc.perform(post("/room/attention/" + room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("API 요청이 성공했습니다."));
    }

    @Test
    @DisplayName("방 나가기 API 테스트")
    void outRoomTest() throws Exception {

        User user1 = User.createUser(1, "testUser1", "test1@example.com");
        userRepository.save(user1);

        User user2 = User.createUser(2, "testUser2", "test2@example.com");
        userRepository.save(user2);

        Room room = Room.create("Test Room", user1.getId(), RoomType.DOUBLE.name());
        roomRepository.save(room);

        UserRoom.Team assignedTeam1 = room.assignTeam(userRoomRepository);
        UserRoom userRoom1 = UserRoom.create(user1.getId(), room.getId(), assignedTeam1);
        userRoomRepository.save(userRoom1);

        UserRoom.Team assignedTeam2 = room.assignTeam(userRoomRepository);
        UserRoom userRoom2 = UserRoom.create(user2.getId(), room.getId(), assignedTeam2);
        userRoomRepository.save(userRoom2);

        String requestBody = """
                {
                    "userId": 1
                }
                """;

        mockMvc.perform(post("/room/out/" + room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("API 요청이 성공했습니다."));
    }

    @Test
    @DisplayName("게임 시작 API 테스트")
    void startRoomTest() throws Exception {
        User user1 = User.createUser(1, "testUser1", "test1@example.com");
        User user2 = User.createUser(2, "testUser2", "test2@example.com");
        userRepository.save(user1);
        userRepository.save(user2);

        Room room = Room.create("Test Room", user1.getId(), Room.RoomType.SINGLE.name());
        roomRepository.save(room);

        UserRoom.Team assignedTeam1 = room.assignTeam(userRoomRepository);
        UserRoom userRoom1 = UserRoom.create(user1.getId(), room.getId(), assignedTeam1);
        userRoomRepository.save(userRoom1);

        UserRoom.Team assignedTeam2 = room.assignTeam(userRoomRepository);
        UserRoom userRoom2 = UserRoom.create(user2.getId(), room.getId(), assignedTeam2);
        userRoomRepository.save(userRoom2);

        String requestBody = """
                {
                    "userId": 1
                }
                """;

        mockMvc.perform(post("/room/start/" + room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("API 요청이 성공했습니다."));
    }

    @Test
    @DisplayName("팀 변경 API 테스트")
    void teamChangeTest() throws Exception {

        User user1 = User.createUser(1, "testUser1", "test1@example.com");
        userRepository.save(user1);

        User user2 = User.createUser(2, "testUser2", "test2@example.com");
        userRepository.save(user2);

        Room room = Room.create("Test Room", user1.getId(), Room.RoomType.DOUBLE.name());
        roomRepository.save(room);

        UserRoom.Team assignedTeam1 = room.assignTeam(userRoomRepository);
        UserRoom userRoom1 = UserRoom.create(user1.getId(), room.getId(), assignedTeam1);
        userRoomRepository.save(userRoom1);

        UserRoom.Team assignedTeam2 = room.assignTeam(userRoomRepository);
        UserRoom userRoom2 = UserRoom.create(user2.getId(), room.getId(), assignedTeam2);
        userRoomRepository.save(userRoom2);

        String requestBody = """
                {
                    "userId": 1
                }
                """;

        mockMvc.perform(put("/team/" + room.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("API 요청이 성공했습니다."));
    }
}
