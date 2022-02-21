package xyz.cryptohows.backend.vc.domain;

import lombok.Builder;
import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class VentureCapital {

    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;

    private final List<Partnership> partnerships = new ArrayList<>();

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
