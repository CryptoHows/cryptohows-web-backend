package xyz.cryptohows.backend.round.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum FundingStage {

    UNKNOWN("unknown"),
    ICO("ico"),
    STRATEGIC("strategic"),
    GROWTH("growth"),
    SEED("seed"),
    PRE_SERIES_A("pre series A"),
    SERIES_A("series A"),
    SERIES_B("series B"),
    SERIES_C("series C"),
    SERIES_D("series D"),
    SERIES_E("series E"),
    EXTENDED_SEED("extended seed"),
    EXTENDED_SERIES_A("extended series A"),
    EXTENDED_SERIES_B("extended series B"),
    EXTENDED_SERIES_C("extended series C"),
    EXTENDED_SERIES_D("extended series D"),
    EXTENDED_SERIES_E("extended series E");

    private final String fundingStage;

    FundingStage(String fundingStage) {
        this.fundingStage = fundingStage;
    }

    public static FundingStage of(String input) {
        return Arrays.stream(values())
                .filter(stage -> stage.getFundingStage().equalsIgnoreCase(input))
                .findAny()
                .orElse(UNKNOWN);
    }

    public static List<String> getAllFundingStages() {
        return Arrays.stream(values())
                .map(FundingStage::getFundingStage)
                .collect(Collectors.toList());
    }
}
