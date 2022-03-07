package xyz.cryptohows.backend.vc.ui.dto;

import lombok.Getter;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.ui.dto.ProjectByCategoryResponse;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.List;
import java.util.Map;

@Getter
public class VentureCapitalResponse {

    private final Long id;
    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;
    private final List<ProjectByCategoryResponse> portfolio;

    public VentureCapitalResponse(Long id, String name, String about, String homepage, String logo, List<ProjectByCategoryResponse> portfolio) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.portfolio = portfolio;
    }

    public static VentureCapitalResponse of(VentureCapital ventureCapital, Map<Category, List<Project>> portfolio) {
        return new VentureCapitalResponse(
                ventureCapital.getId(),
                ventureCapital.getName(),
                ventureCapital.getAbout(),
                ventureCapital.getHomepage(),
                ventureCapital.getLogo(),
                ProjectByCategoryResponse.toListByCategory(portfolio)
        );
    }
}
