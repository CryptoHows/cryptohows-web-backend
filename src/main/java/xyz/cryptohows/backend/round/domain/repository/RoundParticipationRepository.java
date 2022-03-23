package xyz.cryptohows.backend.round.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.List;

@Repository
public interface RoundParticipationRepository extends JpaRepository<RoundParticipation, Long> {

    void deleteByVentureCapital(VentureCapital ventureCapital);

    @Query("select count(distinct roundParticipation.round) " +
            "from RoundParticipation as roundParticipation " +
            "where roundParticipation.ventureCapital.name in (:ventureCapitalNames)")
    Long countRoundsFilterVentureCapitals(@Param("ventureCapitalNames") List<String> ventureCapitalNames);

    @Query("select distinct roundParticipation.round " +
            "from RoundParticipation as roundParticipation " +
            "where roundParticipation.ventureCapital.name in (:ventureCapitalNames) " +
            "order by roundParticipation.round.announcedDate desc")
    List<Round> findRoundsFilterVentureCapitalsOrderByRecentRound(Pageable pageable, @Param("ventureCapitalNames") List<String> ventureCapitalNames);
}
