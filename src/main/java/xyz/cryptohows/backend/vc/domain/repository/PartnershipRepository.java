package xyz.cryptohows.backend.vc.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.List;

@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Long> {

    void deleteByVentureCapital(VentureCapital ventureCapital);

    @Query("select count(distinct partnership.project) " +
            "from Partnership as partnership " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames)")
    Long countProjectFilterVentureCapitals(@Param("ventureCapitalNames") List<String> ventureCapitalNames);

    @Query("select distinct project " +
            "from Partnership as partnership " +
            "join Project as project " +
            "on project = partnership.project " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames) " +
            "order by project.id desc")
    List<Project> findProjectsFilterVentureCapitalsOrderByIdDesc(Pageable pageable, @Param("ventureCapitalNames") List<String> ventureCapitalNames);

    @Query("select count(distinct partnership.project) " +
            "from Partnership as partnership " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames) " +
            "and partnership.project.mainnet in (:mainnets) ")
    Long countProjectFilterMainnetAndVentureCapitals(@Param("mainnets") List<Mainnet> mainnets, @Param("ventureCapitalNames") List<String> ventureCapitalNames);

    @Query("select distinct project " +
            "from Partnership as partnership " +
            "join Project as project " +
            "on project = partnership.project " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames) " +
            "and project.mainnet in (:mainnets) " +
            "order by project.id desc")
    List<Project> findProjectsFilterMainnetAndVentureCapitalsOrderByIdDesc(Pageable pageable, @Param("mainnets") List<Mainnet> mainnets,
                                                                           @Param("ventureCapitalNames") List<String> ventureCapitalNames);

    @Query("select count(distinct partnership.project) " +
            "from Partnership as partnership " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames) " +
            "and partnership.project.category in (:categories) ")
    Long countProjectFilterCategoryAndVentureCapitals(@Param("categories") List<Category> categories, @Param("ventureCapitalNames") List<String> ventureCapitals);

    @Query("select distinct project " +
            "from Partnership as partnership " +
            "join Project as project " +
            "on project = partnership.project " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames) " +
            "and project.category in (:categories) " +
            "order by project.id desc")
    List<Project> findProjectsFilterCategoryAndVentureCapitalsOrderByIdDesc(Pageable pageable, @Param("categories") List<Category> categories,
                                                                            @Param("ventureCapitalNames") List<String> ventureCapitals);

    @Query("select count(distinct partnership.project) " +
            "from Partnership as partnership " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames) " +
            "and partnership.project.category in (:categories) " +
            "and partnership.project.mainnet in (:mainnets) ")
    Long countProjectFilterMainnetAndCategoryAndVentureCapitals(@Param("mainnets") List<Mainnet> mainnets, @Param("categories") List<Category> categories,
                                                                @Param("ventureCapitalNames") List<String> ventureCapitals);

    @Query("select distinct project " +
            "from Partnership as partnership " +
            "join Project as project " +
            "on project = partnership.project " +
            "where partnership.ventureCapital.name in (:ventureCapitalNames) " +
            "and project.category in (:categories) " +
            "and project.mainnet in (:mainnets) " +
            "order by project.id desc")
    List<Project> findProjectsFilterMainnetAndCategoryAndVentureCapitalsOrderByIdDesc(Pageable pageable, @Param("mainnets") List<Mainnet> mainnets,
                                                                                      @Param("categories") List<Category> categories, @Param("ventureCapitalNames") List<String> ventureCapitals);
}
