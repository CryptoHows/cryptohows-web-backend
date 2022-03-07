package xyz.cryptohows.backend.project.domain;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Category {
    NONE("none"),
    EXCHANGES("exchanges"),
    BLOCKCHAIN_INFRASTRUCTURE("blockchainInfrastructure"),
    SECURITY_INFRASTRUCTURE("securityInfrastructure"),
    WALLET("wallet"),
    PAYMENTS("payments"),
    DIGITAL_ASSETS("digitalAssets"),
    SOCIAL_NETWORK("socialNetwork"),
    GAMING("gaming"),
    CEFI("cefi"),
    DEFI("defi"),
    INFRASTRUCTURE("infrastructure"),
    NFTS("nfts"),
    WEB3("web3");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category of(String input) {
        return Arrays.stream(values())
                .filter(category -> category.categoryName.equalsIgnoreCase(input))
                .findAny()
                .orElse(NONE);
    }
}
