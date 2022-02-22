package xyz.cryptohows.backend.round.domain;

import lombok.Getter;

@Getter
public enum FundingStage {
    NONE("none"),
    SEED("seed"),
    SERIES_A("series A"),
    SERIES_B("series B"),
    SERIES_C("series C"),
    SERIES_D("series D"),
    SERIES_E("series E");

    private final String fundingStage;

    FundingStage(String fundingStage) {
        this.fundingStage = fundingStage;
    }
}
