package xyz.cryptohows.backend.project.domain;

import xyz.cryptohows.backend.exception.CryptoHowsException;

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

    public static Mainnet ofRegister(String input) {
        String inputMainnet = input.trim();
        if (inputMainnet.isEmpty()) {
            return NONE;
        }
        return Arrays.stream(values())
                .filter(mainnet -> mainnet.name().equalsIgnoreCase(inputMainnet))
                .findAny()
                .orElseThrow(() -> new CryptoHowsException(inputMainnet + "은 메인넷에 저장되어 있지 않습니다."));
    }

    public static List<String> getAllMainnets() {
        return Arrays.stream(values())
                .map(Enum::toString)
                .collect(Collectors.toList());
    }
}
