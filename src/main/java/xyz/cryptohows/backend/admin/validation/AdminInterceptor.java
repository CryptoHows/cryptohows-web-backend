package xyz.cryptohows.backend.admin.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import xyz.cryptohows.backend.admin.application.AdminService;
import xyz.cryptohows.backend.auth.infrastructure.AuthorizationExtractor;
import xyz.cryptohows.backend.exception.UnauthorizedException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return adminTokenIsRequired(handler) && checkValidToken(request);
    }

    private boolean adminTokenIsRequired(Object handler) {
        AdminTokenRequired adminTokenRequired = ((HandlerMethod) handler).getMethodAnnotation(AdminTokenRequired.class);
        return Objects.isNull(adminTokenRequired);
    }

    private boolean checkValidToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        if (Objects.isNull(token)) {
            throw new UnauthorizedException("권한이 없습니다.");
        }
        adminService.validateToken(token);
        return true;
    }
}
