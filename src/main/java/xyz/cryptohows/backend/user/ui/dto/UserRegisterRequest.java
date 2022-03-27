package xyz.cryptohows.backend.user.ui.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

    private final String email;

    public UserRegisterRequest(String email) {
        this.email = email;
    }
}
