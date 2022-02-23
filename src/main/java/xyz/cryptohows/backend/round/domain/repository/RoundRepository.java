package xyz.cryptohows.backend.round.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.round.domain.Round;

import java.util.List;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.participants " +
            "order by round.announcedDate desc")
    List<Round> findRecentRounds();
}
