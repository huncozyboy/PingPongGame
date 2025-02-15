package prography.pingpong.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import prography.pingpong.domain.exception.InvalidRequestException;
import prography.pingpong.domain.user.dto.response.UserResponse;
import prography.pingpong.domain.user.entity.User;
import prography.pingpong.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserGetService {

    private final UserRepository userRepository;

    @Transactional
    public void findActiveUser(int userId) {
        userRepository.findByIdAndStatus(userId, User.Status.ACTIVE)
                .orElseThrow(InvalidRequestException::new);
    }

    @Transactional
    public Page<UserResponse> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<User> users = userRepository.findAll(pageable);

        return users.map(UserResponse::from);
    }
}
