package xyz.cryptohows.backend.project.application.filterStrategy;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

public enum FilterStrategyFactory {

    NO_FILTER(true, true),
    MAINNET_ONLY(false, true),
    CATEGORY_ONLY(true, false),
    MAINNET_AND_CATEGORY(false, false);

    private final boolean isMainnetEmpty;
    private final boolean isCategoryEmpty;
    private FilterStrategy filterStrategy;

    FilterStrategyFactory(boolean isMainnetEmpty, boolean isCategoryEmpty) {
        this.isMainnetEmpty = isMainnetEmpty;
        this.isCategoryEmpty = isCategoryEmpty;
    }

    public static FilterStrategyFactory of(String mainnet, String category) {
        return Arrays.stream(values())
                .filter(strategy -> strategy.isMainnetEmpty == mainnet.isEmpty())
                .filter(strategy -> strategy.isCategoryEmpty == category.isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("발생할 수 없는 로직입니다."));
    }

    private void setRoundFilterStrategy(FilterStrategy filterStrategy) {
        this.filterStrategy = filterStrategy;
    }

    public FilterStrategy findStrategy() {
        return this.filterStrategy;
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
