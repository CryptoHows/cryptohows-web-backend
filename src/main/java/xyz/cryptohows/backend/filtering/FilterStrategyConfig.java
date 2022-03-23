package xyz.cryptohows.backend.filtering;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.cryptohows.backend.filtering.strategies.*;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;

@Configuration
@RequiredArgsConstructor
public class FilterStrategyConfig {

    private final ProjectRepository projectRepository;
    private final PartnershipRepository partnershipRepository;
    private final RoundRepository roundRepository;
    private final RoundParticipationRepository roundParticipationRepository;

    @Bean
    public NoFilterStrategy createNoFilterStrategy() {
        return new NoFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Bean
    public MainnetFilterStrategy createMainnetFilterStrategy() {
        return new MainnetFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Bean
    public CategoryFilterStrategy createCategoryFilterStrategy() {
        return new CategoryFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Bean
    public VentureCapitalFilterStrategy createVentureCapitalFilterStrategy() {
        return new VentureCapitalFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Bean
    public MainnetAndCategoryFilterStrategy createMainnetAndCategoryFilterStrategy() {
        return new MainnetAndCategoryFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Bean
    public MainnetAndVentureCapitalFilterStrategy createMainnetAndVentureCapitalFilterStrategy() {
        return new MainnetAndVentureCapitalFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Bean
    public CategoryAndVentureCapitalFilterStrategy createCategoryAndVentureCapitalFilterStrategy() {
        return new CategoryAndVentureCapitalFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Bean
    public MainnetAndCategoryAndVentureCapitalFilterStrategy createMainnetAndCategoryAndVentureCapitalFilterStrategy() {
        return new MainnetAndCategoryAndVentureCapitalFilterStrategy(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }
}
