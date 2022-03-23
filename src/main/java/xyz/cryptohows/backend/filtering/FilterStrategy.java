package xyz.cryptohows.backend.filtering;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;

import java.util.Arrays;
import java.util.List;

public abstract class FilterStrategy {

    protected final ProjectRepository projectRepository;
    protected final RoundRepository roundRepository;

    protected FilterStrategy(ProjectRepository projectRepository, RoundRepository roundRepository) {
        this.projectRepository = projectRepository;
        this.roundRepository = roundRepository;
    }

    protected Pageable generateRoundPageable(String order, Integer page, Integer roundsPerPage) {
        if ("old".equals(order)) {
            return PageRequest.of(page, roundsPerPage, Sort.by("announcedDate").ascending());
        }
        return PageRequest.of(page, roundsPerPage, Sort.by("announcedDate").descending());
    }

    protected Pageable generateProjectPageable(Integer page, Integer projectsPerPage) {
        return PageRequest.of(page, projectsPerPage, Sort.by("id").descending());
    }

    protected List<String> parseVentureCapitalInput(String ventureCapitalInput) {
        String[] ventureCapitals = ventureCapitalInput.split(",");
        return Arrays.asList(ventureCapitals);
    }

    public abstract Long countAllRounds(List<Mainnet> mainnets, List<Category> categories);

    public abstract List<Round> findRounds(String order, Integer page, Integer roundsPerPage, List<Mainnet> mainnets, List<Category> categories);

    public abstract Long countAllProjects(List<Mainnet> mainnets, List<Category> categories);

    public abstract List<Project> findProjects(Integer page, Integer projectsPerPage, List<Mainnet> mainnets, List<Category> categories);
}
