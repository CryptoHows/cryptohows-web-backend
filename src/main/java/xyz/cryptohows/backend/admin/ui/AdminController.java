package xyz.cryptohows.backend.admin.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.cryptohows.backend.admin.application.AdminService;
import xyz.cryptohows.backend.admin.ui.dto.AdminLoginRequest;
import xyz.cryptohows.backend.auth.ui.dto.TokenResponse;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginAsAdmin(@RequestBody AdminLoginRequest adminLoginRequest) {
        TokenResponse adminToken = adminService.login(adminLoginRequest);
        return ResponseEntity.ok(adminToken);
    }
}
