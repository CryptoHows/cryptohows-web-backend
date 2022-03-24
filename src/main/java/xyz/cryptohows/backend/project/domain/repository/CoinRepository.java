package xyz.cryptohows.backend.project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.cryptohows.backend.project.domain.Coin;

import java.util.List;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

    @Query("select distinct coin " +
            "from Coin as coin " +
            "join fetch coin.project")
    List<Coin> findAllFetchJoinProject();

    boolean existsByCoinSymbol(String coinSymbol);
}
