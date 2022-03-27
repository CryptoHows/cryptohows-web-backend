package xyz.cryptohows.backend.project.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select distinct project " +
            "from Project as project " +
            "left join fetch project.partnerships ")
    List<Project> findProjects(Pageable pageable);

    @Query("select distinct project " +
            "from Project as project " +
            "left join fetch project.partnerships " +
            "where project.mainnet in (:mainnets)")
    List<Project> findProjectsFilterMainnet(Pageable pageable, @Param("mainnets") List<Mainnet> mainnets);

    @Query("select count(distinct project) " +
            "from Project as project " +
            "where project.mainnet in (:mainnets)")
    Long countProjectsFilterMainnet(@Param("mainnets") List<Mainnet> mainnets);

    @Query("select distinct project " +
            "from Project as project " +
            "left join fetch project.partnerships " +
            "where project.category in (:categories)")
    List<Project> findProjectsFilterCategory(Pageable pageable, @Param("categories") List<Category> categories);

    @Query("select count(distinct project) " +
            "from Project as project " +
            "where project.category in (:categories)")
    Long countProjectsFilterCategory(@Param("categories") List<Category> categories);

    @Query("select distinct project " +
            "from Project as project " +
            "left join fetch project.partnerships " +
            "where project.category in (:categories) " +
            "and project.mainnet in (:mainnets)")
    List<Project> findProjectsFilterMainnetAndCategory(Pageable pageable, @Param("mainnets") List<Mainnet> mainnets,
                                                       @Param("categories") List<Category> categories);

    @Query("select count(distinct project) " +
            "from Project as project " +
            "where project.category in (:categories) " +
            "and project.mainnet in (:mainnets)")
    Long countProjectsFilterMainnetAndCategory(@Param("mainnets") List<Mainnet> mainnets, @Param("categories") List<Category> categories);

    @Query("select project " +
            "from Project as project " +
            "join fetch project.partnerships " +
            "order by project.partnerships.size desc")
    LinkedHashSet<Project> findAllProjectsOrderByNumberOfPartnerships();

    Project findByNameIgnoreCase(String name);

    boolean existsByName(String name);

    @Query("select distinct project " +
            "from Project as project " +
            "join fetch project.partnerships " +
            "where project.id = :projectId ")
    Optional<Project> findByIdFetchJoinPartnerships(@Param("projectId") Long projectId);

    List<Project> findTop5ByNameStartsWithIgnoreCase(String searchWord);

    @Query("select distinct project.category " +
            "from Project as project ")
    List<Category> findAllCategories();

    @Query("select distinct project.mainnet " +
            "from Project as project ")
    List<Mainnet> findAllMainnets();
}
