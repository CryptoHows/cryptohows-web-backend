package xyz.cryptohows.backend.project.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Projects {

    private final List<Project> projects;

    public Projects(List<Project> projects) {
        this.projects = projects;
    }

    public Map<Category, List<Project>> sortProjectsByCategory() {
        Map<Category, List<Project>> projectsByCategory = new LinkedHashMap<>();
        for (Project project : projects) {
            Category category = project.getCategory();
            List<Project> projectsInCategory = projectsByCategory.getOrDefault(category, new ArrayList<>());
            projectsInCategory.add(project);
            projectsByCategory.put(category, projectsInCategory);
        }
        return projectsByCategory;
    }
}
