package xyz.cryptohows.backend.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.cryptohows.backend.admin.ui.dto.AdminLoginRequest;
import xyz.cryptohows.backend.auth.infrastructure.JwtTokenProvider;
import xyz.cryptohows.backend.auth.ui.dto.TokenResponse;
import xyz.cryptohows.backend.exception.UnauthorizedException;

@Transactional
@Service
@RequiredArgsConstructor
public class AdminService {

    private final JwtTokenProvider jwtTokenProvider;

    @Value("${admin.id}")
    private String adminId;

    @Value("${admin.password}")
    private String adminPassword;

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

    public TokenResponse login(AdminLoginRequest adminLoginRequest) {
        String id = adminLoginRequest.getId();
        String password = adminLoginRequest.getPassword();
        if (adminId.equals(id) && adminPassword.equals(password)) {
            return jwtTokenProvider.createToken(adminPayload);
        }
        throw new UnauthorizedException("아이디/비밀번호가 일치하지 않습니다.");
    }
}
