package xyz.cryptohows.backend.project.domain;

import java.util.Arrays;

public enum Mainnet {
    ETHEREUM,
    SOLANA,
    AVALANCHE,
    KLAYTN,
    EOS,
    NONE;

    public static Mainnet of(String input) {
        return Arrays.stream(values())
                .filter(mainnet -> mainnet.name().equalsIgnoreCase(input))
                .findAny()
                .orElse(NONE);
    }
}
