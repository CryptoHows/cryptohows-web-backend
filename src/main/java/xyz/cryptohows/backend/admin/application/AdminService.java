package xyz.cryptohows.backend.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.cryptohows.backend.auth.infrastructure.JwtTokenProvider;
import xyz.cryptohows.backend.exception.UnauthorizedException;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${admin.payload}")
    private String adminPayload;

    public void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new UnauthorizedException("유효하지 않은 토큰입니다.");
        }
        String tokenPayload = jwtTokenProvider.getPayload(token);
        if (!adminPayload.equals(tokenPayload)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
    }
}
