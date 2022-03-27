package xyz.cryptohows.backend.user.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import xyz.cryptohows.backend.user.domain.User;
import xyz.cryptohows.backend.user.domain.repository.UserRepository;
import xyz.cryptohows.backend.user.ui.dto.UserRegisterRequest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("이메일 정보를 담아 유저를 생성한다.")
    @Test
    void register() {
        // given
        String email = "joel@joel.com";
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(email);

        // when
        userService.register(userRegisterRequest);

        // then
        User user = userRepository.findByEmail(email).get();
        assertThat(user.getEmail()).isEqualTo(email);
    }

    @DisplayName("이메일이 이미 등록이 된 것이라면 추가로 등록하지 않는다.")
    @Test
    void registerDuplicate() {
        // given
        String email = "joel@joel.com";
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest(email);
        userService.register(userRegisterRequest);

        // when
        userService.register(userRegisterRequest);

        // then
        long count = userRepository.count();
        assertThat(count).isEqualTo(1L);
    }
}
