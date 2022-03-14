package xyz.cryptohows.backend.round.application.filterStrategy;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

@Configuration
@RequiredArgsConstructor
public class RoundFilterStrategyConfig {

    private final RoundRepository roundRepository;

    @Bean
    public NoFilterStrategy createNoFilterStrategy() {
        return new NoFilterStrategy(roundRepository);
    }

    @Bean
    public MainnetFilterStrategy createMainnetFilterStrategy() {
        return new MainnetFilterStrategy(roundRepository);
    }

    @Bean
    public CategoryFilterStrategy createCategoryFilterStrategy() {
        return new CategoryFilterStrategy(roundRepository);
    }

    @Bean
    public MainnetAndCategoryFilterStrategy createMainnetAndCategoryFilterStrategy() {
        return new MainnetAndCategoryFilterStrategy(roundRepository);
    }
}
