package xyz.cryptohows.backend.project.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Project project;

    private String coinSymbol;
    private String coinInformation;

    @Builder
    public Coin(Long id, Project project, String coinSymbol, String coinInformation) {
        this.id = id;
        this.project = project;
        this.coinSymbol = coinSymbol;
        this.coinInformation = coinInformation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coin coin = (Coin) o;
        return Objects.equals(id, coin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
