package xyz.cryptohows.backend.round.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.round.domain.RoundParticipation;

@Repository
public interface RoundParticipationRepository extends JpaRepository<RoundParticipation, Long> {
}
