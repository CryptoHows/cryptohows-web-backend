package xyz.cryptohows.backend.vc.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.cryptohows.backend.project.domain.Project;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

    @OneToMany(mappedBy = "ventureCapital", cascade = CascadeType.REMOVE)
    private Set<Partnership> partnerships = new HashSet<>();

    @Builder
    public VentureCapital(Long id, String name, String about, String homepage, String logo) {
        this.id = id;
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

    public void updateInformation(String name, String about, String homepage, String logo) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentureCapital that = (VentureCapital) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
