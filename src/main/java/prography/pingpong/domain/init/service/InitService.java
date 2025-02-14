package prography.pingpong.domain.init.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import prography.pingpong.domain.init.dto.request.InitRequest;
import prography.pingpong.domain.init.dto.response.FakerResponse;
import prography.pingpong.domain.room.repository.RoomRepository;
import prography.pingpong.domain.user.entity.User;
import prography.pingpong.domain.user.repository.UserRepository;
import prography.pingpong.domain.userRoom.repository.UserRoomRepository;

@Service
@RequiredArgsConstructor
public class InitService {

    private final UserRoomRepository userRoomRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final RestTemplate restTemplate;

    public void initializeData(InitRequest request) {

        userRoomRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();

        FakerResponse response = restTemplate.getForObject(
                "/users?_seed=" + request.seed() + "&_quantity=" + request.quantity() + "&_locale=ko_KR",
                FakerResponse.class
        );

        if (response != null && response.data() != null) {
            response.data().forEach(fakerUser -> {
                User user = User.createUser(fakerUser.id(), fakerUser.username(), fakerUser.email());
                userRepository.save(user);
            });
        }
    }
}
