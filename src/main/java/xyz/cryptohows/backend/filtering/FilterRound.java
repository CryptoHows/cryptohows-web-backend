package xyz.cryptohows.backend.filtering;

import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.round.domain.Round;

import java.util.List;

public interface FilterRound {

    Long countAllRounds(List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);

    List<Round> findRounds(String order, Integer page, Integer roundsPerPage, List<Mainnet> mainnets, List<Category> categories, String ventureCapitalInput);

}
