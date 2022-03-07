package xyz.cryptohows.backend.vc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.vc.domain.Partnership;

@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
}
