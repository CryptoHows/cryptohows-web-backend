package xyz.cryptohows.backend.project.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select distinct project " +
            "from Project as project " +
            "left join fetch project.partnerships")
    List<Project> findProjectsFetchJoinPartnerships(Pageable pageable);

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
}
