package xyz.cryptohows.backend.round.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.round.domain.Round;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants ")
    List<Round> findRecentRounds(Pageable pageable);

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project ")
    List<Round> findAllFetchJoinProject();

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where round.id = :roundId ")
    Optional<Round> findRoundByIdFetchJoinProjectAndParticipants(@Param("roundId") Long roundId);
}
