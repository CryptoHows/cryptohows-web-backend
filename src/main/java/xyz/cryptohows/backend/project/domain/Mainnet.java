package xyz.cryptohows.backend.project.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Mainnet {
    ALGORAND,
    ARWEAVE,
    BITCOIN,
    BSC,
    CELO,
    COSMOS,
    DESO,
    ETHEREUM,
    FLOW,
    HELIUM,
    KLAYTN,
    NEAR,
    POLKADOT,
    RONIN,
    SOLANA,
    TERRA,
    THUNDERCORE,
    NONE;

    public static Mainnet of(String input) {
        return Arrays.stream(values())
                .filter(mainnet -> mainnet.name().equalsIgnoreCase(input))
                .findAny()
                .orElse(NONE);
    }

    public static List<String> getAllMainnets() {
        return Arrays.stream(values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}
