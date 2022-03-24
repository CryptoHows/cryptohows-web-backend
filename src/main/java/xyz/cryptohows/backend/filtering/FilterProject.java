package xyz.cryptohows.backend.filtering;

import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.List;

public interface FilterProject {

    Long countAllProjects(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);

    List<Project> findProjects(Integer page, Integer projectsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);

}
