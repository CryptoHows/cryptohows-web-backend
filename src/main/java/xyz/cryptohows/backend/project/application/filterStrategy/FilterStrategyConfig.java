package xyz.cryptohows.backend.project.application.filterStrategy;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    public MainnetAndCategoryFilterStrategy createMainnetAndCategoryFilterStrategy() {
        return new MainnetAndCategoryFilterStrategy(projectRepository, roundRepository);
    }
}
