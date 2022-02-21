package xyz.cryptohows.backend.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.project.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
