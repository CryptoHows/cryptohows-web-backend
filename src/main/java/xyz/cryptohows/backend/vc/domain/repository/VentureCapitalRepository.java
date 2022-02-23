package xyz.cryptohows.backend.vc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.Optional;

@Repository
public interface VentureCapitalRepository extends JpaRepository<VentureCapital, Long> {

    @Query("select distinct ventureCapital " +
            "from VentureCapital as ventureCapital " +
            "join fetch ventureCapital.partnerships " +
            "where ventureCapital.id = :vcId ")
    Optional<VentureCapital> findByIdFetchJoinPartnerships(@Param("vcId") Long vcId);
}
