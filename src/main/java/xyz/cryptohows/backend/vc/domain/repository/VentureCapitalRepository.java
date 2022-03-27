package xyz.cryptohows.backend.vc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentureCapitalRepository extends JpaRepository<VentureCapital, Long> {

    @Query("select distinct ventureCapital " +
            "from VentureCapital as ventureCapital " +
            "join fetch ventureCapital.partnerships " +
            "where ventureCapital.id = :vcId ")
    Optional<VentureCapital> findByIdFetchJoinPartnerships(@Param("vcId") Long vcId);

    List<VentureCapital> findAllByNameInIgnoreCase(List<String> ventureCapitalNames);

    boolean existsByName(String name);

    @Query("select distinct ventureCapital.name " +
            "from VentureCapital as ventureCapital " +
            "order by ventureCapital.name asc ")
    List<String> findAllNames();
}
