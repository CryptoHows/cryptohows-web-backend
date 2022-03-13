package xyz.cryptohows.backend.vc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.vc.domain.Partnership;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

@Repository
public interface PartnershipRepository extends JpaRepository<Partnership, Long> {
    void deleteByVentureCapital(VentureCapital ventureCapital);
}
