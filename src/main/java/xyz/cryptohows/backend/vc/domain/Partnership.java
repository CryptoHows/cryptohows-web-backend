package xyz.cryptohows.backend.vc.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.project.domain.Project;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Partnership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private VentureCapital ventureCapital;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    public Partnership(VentureCapital ventureCapital, Project project) {
        this.ventureCapital = ventureCapital;
        this.project = project;
    }

    public boolean isSameProject(Project project) {
        return this.project.equals(project);
    }
}
