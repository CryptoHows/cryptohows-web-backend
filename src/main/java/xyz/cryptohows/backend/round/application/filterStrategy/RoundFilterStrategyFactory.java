package xyz.cryptohows.backend.round.application.filterStrategy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

public enum RoundFilterStrategyFactory {

    NO_FILTER(true, true),
    MAINNET_ONLY(false, true),
    CATEGORY_ONLY(true, false),
    MAINNET_AND_CATEGORY(false, false);

    private final boolean isMainnetEmpty;
    private final boolean isCategoryEmpty;
    private RoundFilterStrategy roundFilterStrategy;

    RoundFilterStrategyFactory(boolean isMainnetEmpty, boolean isCategoryEmpty) {
        this.isMainnetEmpty = isMainnetEmpty;
        this.isCategoryEmpty = isCategoryEmpty;
    }

    private void setRoundFilterStrategy(RoundFilterStrategy roundFilterStrategy) {
        this.roundFilterStrategy = roundFilterStrategy;
    }

    public RoundFilterStrategy findStrategy() {
        return this.roundFilterStrategy;
    }

    public static RoundFilterStrategyFactory of(String mainnet, String category) {
        return Arrays.stream(values())
                .filter(strategy -> strategy.isMainnetEmpty == mainnet.isEmpty())
                .filter(strategy -> strategy.isCategoryEmpty == category.isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("발생할 수 없는 로직입니다."));
    }

    @Component
    @AllArgsConstructor
    private static class StrategyInjector {
        private NoFilterStrategy noFilterStrategy;
        private MainnetFilterStrategy mainnetFilterStrategy;
        private CategoryFilterStrategy categoryFilterStrategy;
        private MainnetAndCategoryFilterStrategy mainnetAndCategoryFilterStrategy;

        @PostConstruct
        private void inject() {
            NO_FILTER.setRoundFilterStrategy(noFilterStrategy);
            MAINNET_ONLY.setRoundFilterStrategy(mainnetFilterStrategy);
            CATEGORY_ONLY.setRoundFilterStrategy(categoryFilterStrategy);
            MAINNET_AND_CATEGORY.setRoundFilterStrategy(mainnetAndCategoryFilterStrategy);
        }
    }
}
