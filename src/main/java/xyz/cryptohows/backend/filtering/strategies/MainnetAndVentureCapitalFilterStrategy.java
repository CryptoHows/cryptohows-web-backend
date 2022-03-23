package xyz.cryptohows.backend.filtering.strategies;

import org.springframework.data.domain.Pageable;
import xyz.cryptohows.backend.filtering.FilterStrategy;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;

import java.util.List;

public class MainnetAndVentureCapitalFilterStrategy extends FilterStrategy {

    public MainnetAndVentureCapitalFilterStrategy(ProjectRepository projectRepository, PartnershipRepository partnershipRepository,
                                                  RoundRepository roundRepository, RoundParticipationRepository roundParticipationRepository) {
        super(projectRepository, partnershipRepository, roundRepository, roundParticipationRepository);
    }

    @Override
    public Long countAllRounds(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        List<String> ventureCapitals = parseVentureCapitalInput(ventureCapitalInput);
        return roundParticipationRepository.countRoundsFilterMainnetAndVentureCapitals(mainnets, ventureCapitals);
    }

    @Override
    public List<Round> findRounds(String order, Integer page, Integer roundsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        List<String> ventureCapitals = parseVentureCapitalInput(ventureCapitalInput);
        Pageable pageable = generatePageable(page, roundsPerPage);
        if ("old".equals(order)) {
            return roundParticipationRepository.findRoundsFilterMainnetAndVentureCapitalsOrderByOlderRound(pageable, mainnets, ventureCapitals);
        }
        return roundParticipationRepository.findRoundsFilterMainnetAndVentureCapitalsOrderByRecentRound(pageable, mainnets, ventureCapitals);
    }

    @Override
    public Long countAllProjects(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        List<String> ventureCapitals = parseVentureCapitalInput(ventureCapitalInput);
        return partnershipRepository.countProjectFilterMainnetAndVentureCapitals(mainnets, ventureCapitals);
    }

    @Override
    public List<Project> findProjects(Integer page, Integer projectsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput) {
        List<String> ventureCapitals = parseVentureCapitalInput(ventureCapitalInput);
        Pageable pageable = generatePageable(page, projectsPerPage);
        return partnershipRepository.findProjectsFilterMainnetAndVentureCapitalsOrderByIdDesc(pageable, mainnets, ventureCapitals);
    }
}
