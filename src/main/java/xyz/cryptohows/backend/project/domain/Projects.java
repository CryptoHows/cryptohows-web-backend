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
        for (Category category : Category.values()) {
            projectsByCategory.put(category, new ArrayList<>());
        }

        for (Project project : projects) {
            List<Project> projectsInCategory = projectsByCategory.get(project.getCategory());
            projectsInCategory.add(project);
        }
        return projectsByCategory;
    }
}
