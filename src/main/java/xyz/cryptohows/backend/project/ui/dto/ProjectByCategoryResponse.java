package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class ProjectByCategoryResponse {

    private final String category;
    private final List<ProjectSimpleResponse> projects;

    public ProjectByCategoryResponse(String category, List<ProjectSimpleResponse> projects) {
        this.category = category;
        this.projects = projects;
    }

    public static ProjectByCategoryResponse of(Category category, List<Project> portfolio) {
        return new ProjectByCategoryResponse(
                category.getCategory(),
                ProjectSimpleResponse.toList(portfolio)
        );
    }

    public static List<ProjectByCategoryResponse> toListByCategory(Map<Category, List<Project>> portfolio) {
        List<ProjectByCategoryResponse> projectByCategoryResponses = new ArrayList<>();

        for (Map.Entry<Category, List<Project>> portfolioByCategory : portfolio.entrySet()) {
            projectByCategoryResponses.add(
                    ProjectByCategoryResponse.of(
                            portfolioByCategory.getKey(),
                            portfolioByCategory.getValue()
                    )
            );
        }
        return projectByCategoryResponses;
    }
}
