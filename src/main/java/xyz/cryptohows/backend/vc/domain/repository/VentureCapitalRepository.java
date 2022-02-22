package xyz.cryptohows.backend.vc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.Optional;

@Repository
public interface VentureCapitalRepository extends JpaRepository<VentureCapital, Long> {
    Optional<VentureCapital> findByName(String name);
}
