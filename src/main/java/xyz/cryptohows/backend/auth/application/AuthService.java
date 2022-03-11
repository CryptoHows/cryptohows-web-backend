package xyz.cryptohows.backend.auth.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.cryptohows.backend.auth.infrastructure.JwtTokenProvider;
import xyz.cryptohows.backend.exception.UnauthorizedException;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;

    public void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }
    }
}
