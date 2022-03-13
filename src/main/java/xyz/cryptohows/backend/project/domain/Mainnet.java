package xyz.cryptohows.backend.project.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Mainnet {
    ETHEREUM,
    SOLANA,
    AVALANCHE,
    KLAYTN,
    EOS,
    ALGORAND,
    ARWEAVE,
    BITCOIN,
    BSC,
    CELO,
    COSMOS,
    DESO,
    FLOW,
    HELIUM,
    NEAR,
    POLKADOT,
    RONIN,
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
