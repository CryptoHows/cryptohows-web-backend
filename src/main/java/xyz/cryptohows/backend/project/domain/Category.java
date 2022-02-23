package xyz.cryptohows.backend.project.domain;

import lombok.Getter;

@Getter
public enum Category {
    EXCHANGES("exchanges"),
    BLOCKCHAIN_INFRASTRUCTURE("blockchainInfrastructure"),
    SECURITY_INFRASTRUCTURE("securityInfrastructure"),
    WALLET("wallet"),
    PAYMENTS("payments"),
    DIGITAL_ASSETS("digitalAssets"),
    SOCIAL_NETWORK("socialNetwork"),
    GAMING("gaming");

    private final String category;

    Category(String category) {
        this.category = category;
    }
}
