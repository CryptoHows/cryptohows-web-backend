package xyz.cryptohows.backend.round.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

@Repository
public interface RoundParticipationRepository extends JpaRepository<RoundParticipation, Long> {
    void deleteByVentureCapital(VentureCapital ventureCapital);
}
