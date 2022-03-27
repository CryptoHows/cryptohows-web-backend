package xyz.cryptohows.backend.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.user.domain.User;
import xyz.cryptohows.backend.user.domain.repository.UserRepository;
import xyz.cryptohows.backend.user.ui.dto.UserRegisterRequest;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    public final UserRepository userRepository;

    public void register(UserRegisterRequest userRegisterRequest) {
        String email = userRegisterRequest.getEmail();
        Optional<User> userByEmail = userRepository.findByEmail(email);
        if (userByEmail.isPresent()) {
            return;
        }
        User user = new User(email);
        userRepository.save(user);
    }
}
