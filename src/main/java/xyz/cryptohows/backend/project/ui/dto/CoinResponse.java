package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Coin;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CoinResponse {

    private final Long id;
    private final String coinSymbol;
    private final String coinInformation;

    public CoinResponse(Long id, String coinSymbol, String coinInformation) {
        this.id = id;
        this.coinSymbol = coinSymbol;
        this.coinInformation = coinInformation;
    }

    public static CoinResponse of(Coin coin) {
        return new CoinResponse(
                coin.getId(),
                coin.getCoinSymbol(),
                coin.getCoinInformation()
        );
    }

    public static List<CoinResponse> toList(List<Coin> coins) {
        return coins.stream()
                .map(CoinResponse::of)
                .collect(Collectors.toList());
    }
}
