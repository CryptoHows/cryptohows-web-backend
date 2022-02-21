package xyz.cryptohows.backend.vc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.project.domain.Project;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VentureCapital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String about;
    private String homepage;
    private String logo;

    @OneToMany(mappedBy = "ventureCapital")
    private List<Partnership> partnerships = new ArrayList<>();

    @Builder
    public VentureCapital(String name, String about, String homepage, String logo) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
    }

    public void makePartnership(Project project) {
        Partnership partnership = new Partnership(this, project);
        partnerships.add(partnership);
        project.addPartnership(partnership);
    }

    public void makePartnerships(List<Project> projects) {
        for (Project project : projects) {
            makePartnership(project);
        }
    }

    public List<Project> getPortfolio() {
        return partnerships.stream()
                .map(Partnership::getProject)
                .collect(Collectors.toList());
    }
}
