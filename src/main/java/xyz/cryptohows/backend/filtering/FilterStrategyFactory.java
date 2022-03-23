package xyz.cryptohows.backend.filtering;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.cryptohows.backend.filtering.strategies.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;

public enum FilterStrategyFactory {

    NO_FILTER(true, true, true),
    MAINNET_ONLY(false, true, true),
    CATEGORY_ONLY(true, false, true),
    VENTURE_CAPITAL_ONLY(true, true, false),
    MAINNET_AND_CATEGORY(false, false, true),
    MAINNET_AND_VENTURE_CAPITAL(false, true, false),
    CATEGORY_AND_VENTURE_CAPITAL(true, false, false),
    MAINNET_AND_CATEGORY_AND_VENTURE_CAPITAL(true, true, true);

    private final boolean isMainnetEmpty;
    private final boolean isCategoryEmpty;
    private final boolean isVentureCaptialEmpty;
    private FilterStrategy filterStrategy;

    FilterStrategyFactory(boolean isMainnetEmpty, boolean isCategoryEmpty, boolean isVentureCaptialEmpty) {
        this.isMainnetEmpty = isMainnetEmpty;
        this.isCategoryEmpty = isCategoryEmpty;
        this.isVentureCaptialEmpty = isVentureCaptialEmpty;
    }

    public static FilterStrategyFactory of(String mainnet, String category, String ventureCapital) {
        return Arrays.stream(values())
                .filter(strategy -> strategy.isMainnetEmpty == mainnet.isEmpty())
                .filter(strategy -> strategy.isCategoryEmpty == category.isEmpty())
                .filter(strategy -> strategy.isVentureCaptialEmpty == ventureCapital.isEmpty())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("발생할 수 없는 로직입니다."));
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
        private VentureCapitalFilterStrategy ventureCapitalFilterStrategy;
        private MainnetAndCategoryFilterStrategy mainnetAndCategoryFilterStrategy;
        private MainnetAndVentureCapitalFilterStrategy mainnetAndVentureCapitalFilterStrategy;
        private CategoryAndVentureCapitalFilterStrategy categoryAndVentureCapitalFilterStrategy;
        private MainnetAndCategoryAndVentureCapitalFilterStrategy mainnetAndCategoryAndVentureCapitalFilterStrategy;

        @PostConstruct
        private void inject() {
            NO_FILTER.filterStrategy = noFilterStrategy;
            MAINNET_ONLY.filterStrategy = mainnetFilterStrategy;
            CATEGORY_ONLY.filterStrategy = categoryFilterStrategy;
            VENTURE_CAPITAL_ONLY.filterStrategy = ventureCapitalFilterStrategy;
            MAINNET_AND_CATEGORY.filterStrategy = mainnetAndCategoryFilterStrategy;
            MAINNET_AND_VENTURE_CAPITAL.filterStrategy = mainnetAndVentureCapitalFilterStrategy;
            CATEGORY_AND_VENTURE_CAPITAL.filterStrategy = categoryAndVentureCapitalFilterStrategy;
            MAINNET_AND_CATEGORY_AND_VENTURE_CAPITAL.filterStrategy = mainnetAndCategoryAndVentureCapitalFilterStrategy;
        }
    }
}
