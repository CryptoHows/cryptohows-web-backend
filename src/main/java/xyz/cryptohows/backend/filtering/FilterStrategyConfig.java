package xyz.cryptohows.backend.filtering;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.cryptohows.backend.filtering.strategies.*;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

@Configuration
@RequiredArgsConstructor
public class FilterStrategyConfig {

    private final ProjectRepository projectRepository;
    private final RoundRepository roundRepository;

    @Bean
    public NoFilterStrategy createNoFilterStrategy() {
        return new NoFilterStrategy(projectRepository, roundRepository);
    }

    @Bean
    public MainnetFilterStrategy createMainnetFilterStrategy() {
        return new MainnetFilterStrategy(projectRepository, roundRepository);
    }

    @Bean
    public CategoryFilterStrategy createCategoryFilterStrategy() {
        return new CategoryFilterStrategy(projectRepository, roundRepository);
    }

    @Bean
    public VentureCapitalFilterStrategy createVentureCapitalFilterStrategy() {
        return new VentureCapitalFilterStrategy(projectRepository, roundRepository);
    }

    @Bean
    public MainnetAndCategoryFilterStrategy createMainnetAndCategoryFilterStrategy() {
        return new MainnetAndCategoryFilterStrategy(projectRepository, roundRepository);
    }

    @Bean
    public MainnetAndVentureCapitalFilterStrategy createMainnetAndVentureCapitalFilterStrategy() {
        return new MainnetAndVentureCapitalFilterStrategy(projectRepository, roundRepository);
    }

    @Bean
    public CategoryAndVentureCapitalFilterStrategy createCategoryAndVentureCapitalFilterStrategy() {
        return new CategoryAndVentureCapitalFilterStrategy(projectRepository, roundRepository);
    }

    @Bean
    public MainnetAndCategoryAndVentureCapitalFilterStrategy createMainnetAndCategoryAndVentureCapitalFilterStrategy() {
        return new MainnetAndCategoryAndVentureCapitalFilterStrategy(projectRepository, roundRepository);
    }
}
