package xyz.cryptohows.backend.filtering;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.vc.domain.repository.PartnershipRepository;

import java.util.Arrays;
import java.util.List;

public abstract class FilterStrategy {

    protected final ProjectRepository projectRepository;
    protected final PartnershipRepository partnershipRepository;
    protected final RoundRepository roundRepository;
    protected final RoundParticipationRepository roundParticipationRepository;

    protected FilterStrategy(ProjectRepository projectRepository, PartnershipRepository partnershipRepository,
                             RoundRepository roundRepository, RoundParticipationRepository roundParticipationRepository) {
        this.projectRepository = projectRepository;
        this.partnershipRepository = partnershipRepository;
        this.roundRepository = roundRepository;
        this.roundParticipationRepository = roundParticipationRepository;
    }

    protected Pageable generatePageableSortByAnnouncedDate(String order, Integer page, Integer itemPerPage) {
        if ("old".equals(order)) {
            return PageRequest.of(page, itemPerPage, Sort.by("announcedDate").ascending());
        }
        return PageRequest.of(page, itemPerPage, Sort.by("announcedDate").descending());
    }

    protected Pageable generatePageableSortById(Integer page, Integer itemPerPage) {
        return PageRequest.of(page, itemPerPage, Sort.by("id").descending());
    }

    protected Pageable generatePageable(Integer page, Integer itemPerPage) {
        return PageRequest.of(page, itemPerPage);
    }

    protected List<String> parseVentureCapitalInput(String ventureCapitalInput) {
        String[] ventureCapitals = ventureCapitalInput.split(",");
        return Arrays.asList(ventureCapitals);
    }

    public abstract Long countAllRounds(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);

    public abstract List<Round> findRounds(String order, Integer page, Integer roundsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);

    public abstract Long countAllProjects(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);

    public abstract List<Project> findProjects(Integer page, Integer projectsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);
}
