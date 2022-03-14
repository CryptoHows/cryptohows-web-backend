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
            "where round.project.mainnet = :mainnet")
    List<Round> findRoundsFilterMainnet(Pageable pageable, @Param("mainnet") Mainnet mainnet);

    @Query("select count(distinct round) " +
            "from Round as round " +
            "where round.project.mainnet = :mainnet")
    Long countRoundsFilterMainnet(@Param("mainnet") Mainnet mainnet);

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where round.project.category = :category")
    List<Round> findRoundsFilterCategory(Pageable pageable, @Param("category") Category category);

    @Query("select count(distinct round) " +
            "from Round as round " +
            "where round.project.category = :category")
    Long countRoundsFilterCategory(@Param("category") Category category);

    @Query("select distinct round " +
            "from Round as round " +
            "join fetch round.project " +
            "join fetch round.vcParticipants " +
            "where round.project.category = :category " +
            "and round.project.mainnet = :mainnet")
    List<Round> findRoundsFilterMainnetAndCategory(Pageable pageable, @Param("mainnet") Mainnet mainnet, @Param("category") Category category);

    @Query("select count(distinct round)  " +
            "from Round as round " +
            "where round.project.category = :category " +
            "and round.project.mainnet = :mainnet")
    Long countRoundsFilterMainnetAndCategory(@Param("mainnet") Mainnet mainnet, @Param("category") Category category);

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
