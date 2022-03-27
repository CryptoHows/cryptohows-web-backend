package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Coin;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CoinFullResponse {

    private final CoinResponse coin;
    private final ProjectSearchResponse project;

    public CoinFullResponse(CoinResponse coin, ProjectSearchResponse project) {
        this.coin = coin;
        this.project = project;
    }

    public static CoinFullResponse of(Coin coin) {
        return new CoinFullResponse(
                CoinResponse.of(coin),
                ProjectSearchResponse.of(coin.getProject())
        );
    }

    public static List<CoinFullResponse> toList(List<Coin> coins) {
        return coins.stream()
                .map(CoinFullResponse::of)
                .collect(Collectors.toList());
    }
}
