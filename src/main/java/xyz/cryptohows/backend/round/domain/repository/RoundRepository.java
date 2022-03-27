package xyz.cryptohows.backend.round.domain.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.round.domain.Round;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoundRepository extends JpaRepository<Round, Long> {

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants ")
    List<Round> findRounds(Pageable pageable);

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where round.project.mainnet in (:mainnets)")
    List<Round> findRoundsFilterMainnet(Pageable pageable, @Param("mainnets") List<Mainnet> mainnets);

    @Query("select count(distinct round) " +
            "from Round as round " +
            "where round.project.mainnet in (:mainnets)")
    Long countRoundsFilterMainnet(@Param("mainnets") List<Mainnet> mainnets);

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where round.project.category in (:categories)")
    List<Round> findRoundsFilterCategory(Pageable pageable, @Param("categories") List<Category> categories);

    @Query("select count(distinct round) " +
            "from Round as round " +
            "where round.project.category in (:categories)")
    Long countRoundsFilterCategory(@Param("categories") List<Category> categories);

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where round.project.category in (:categories) " +
            "and round.project.mainnet in (:mainnets)")
    List<Round> findRoundsFilterMainnetAndCategory(Pageable pageable, @Param("mainnets") List<Mainnet> mainnet,
                                                   @Param("categories") List<Category> category);

    @Query("select count(distinct round)  " +
            "from Round as round " +
            "where round.project.category in (:categories) " +
            "and round.project.mainnet in (:mainnets)")
    Long countRoundsFilterMainnetAndCategory(@Param("mainnets") List<Mainnet> mainnets, @Param("categories") List<Category> categories);

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "order by round.announcedDate desc ")
    List<Round> findAllFetchJoinProject();

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where round.id = :roundId ")
    Optional<Round> findRoundByIdFetchJoinProjectAndParticipants(@Param("roundId") Long roundId);

    @Query("select count(distinct round)  " +
            "from Round as round " +
            "where size(round.project.coins) > 0 ")
    Long countCoinAvailableRounds();

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where size(round.project.coins) > 0 " +
            "order by round.announcedDate desc")
    List<Round> findCoinAvailableRoundsSortByRecent(Pageable pageable);
}
