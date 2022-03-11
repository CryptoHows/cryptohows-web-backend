package xyz.cryptohows.backend.auth;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.cryptohows.backend.admin.application.AdminService;
import xyz.cryptohows.backend.admin.validation.AdminInterceptor;

@Configuration
@RequiredArgsConstructor
public class AuthenticationPrincipalConfig implements WebMvcConfigurer {

    private final AdminService adminService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(generateAdminInterceptor())
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");
    }

    @Bean
    public AdminInterceptor generateAdminInterceptor() {
        return new AdminInterceptor(adminService);
    }
}
