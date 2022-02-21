package xyz.cryptohows.backend.vc.domain;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;

@Getter
public class Partnership {

    private final VentureCapital ventureCapital;
    private final Project project;

    public Partnership(VentureCapital ventureCapital, Project project) {
        this.ventureCapital = ventureCapital;
        this.project = project;
    }

    public boolean isSameProject(Project project) {
        return this.project.equals(project);
    }
}
