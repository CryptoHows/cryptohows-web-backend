package xyz.cryptohows.backend.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.LinkedHashSet;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select project " +
            "from Project as project " +
            "inner join fetch project.partnerships " +
            "order by project.partnerships.size desc")
    LinkedHashSet<Project> findAllProjectsOrderByNumberOfPartnerships();
}
