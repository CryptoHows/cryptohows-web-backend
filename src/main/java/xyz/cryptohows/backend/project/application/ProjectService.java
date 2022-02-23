package xyz.cryptohows.backend.project.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.project.ui.dto.ProjectResponse;

import java.util.LinkedHashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<ProjectResponse> findAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return ProjectResponse.toList(projects);
    }

    public List<ProjectResponse> orderProjectByNumberOfInvestors() {
        LinkedHashSet<Project> projects = projectRepository.findAllProjectsOrderByNumberOfPartnerships();
        return ProjectResponse.toList(projects);
    }
}
