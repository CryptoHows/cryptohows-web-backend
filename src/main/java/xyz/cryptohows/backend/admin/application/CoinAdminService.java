package xyz.cryptohows.backend.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.upload.CoinUploadService;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.ui.dto.CoinFullResponse;
import xyz.cryptohows.backend.admin.ui.dto.CoinRequest;
import xyz.cryptohows.backend.project.domain.Coin;
import xyz.cryptohows.backend.project.domain.repository.CoinRepository;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class CoinAdminService {

    private final CoinUploadService coinUploadService;
    private final CoinRepository coinRepository;

    public List<CoinFullResponse> findAll() {
        List<Coin> coins = coinRepository.findAllFetchJoinProject();
        return CoinFullResponse.toList(coins);
    }

    public void create(CoinRequest coinRequest) {
        Project project = coinUploadService.findProject(coinRequest.getProject());

        Coin coin = Coin.builder()
                .project(project)
                .coinSymbol(coinRequest.getCoinSymbol())
                .coinInformation(coinRequest.getCoinInformation())
                .build();

        coinUploadService.checkExistenceAndSave(coin);
    }

    public void updateById(Long coinId, CoinRequest coinRequest) {
        deleteById(coinId);
        create(coinRequest);
    }

    public void deleteById(Long coinId) {
        Coin coin = findCoinById(coinId);
        coinRepository.delete(coin);
    }

    private Coin findCoinById(Long coinId) {
        return coinRepository.findById(coinId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 코인은 없습니다."));
    }

    public void uploadExcel(MultipartFile file) {
        coinUploadService.uploadCoins(file);
    }
}
