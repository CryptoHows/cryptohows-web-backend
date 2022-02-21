package xyz.cryptohows.backend.round.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.round.domain.Round;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {
}
