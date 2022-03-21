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
    FANTOM,
    NONE;

    public static Mainnet of(String input) {
        return Arrays.stream(values())
                .filter(mainnet -> mainnet.name().equalsIgnoreCase(input.trim()))
                .findAny()
                .orElse(NONE);
    }

    public static List<Mainnet> parseIn(String input) {
        String[] inputs = input.split(",");
        return Arrays.stream(inputs)
                .map(Mainnet::of)
                .filter(Mainnet::isNotNone)
                .collect(Collectors.toList());
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
                .filter(Mainnet::isNotNone)
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    private static boolean isNotNone(Mainnet mainnet) {
        return mainnet != Mainnet.NONE;
    }

    public static List<String> toStringList(List<Mainnet> mainnets) {
        return mainnets.stream()
                .filter(Mainnet::isNotNone)
                .map(Mainnet::name)
                .collect(Collectors.toList());
    }
}
