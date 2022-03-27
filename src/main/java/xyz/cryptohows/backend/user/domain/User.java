package xyz.cryptohows.backend.user.domain;

import lombok.Getter;
import xyz.cryptohows.backend.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    public User(String email) {
        this.email = email;
    }
}
