package xyz.cryptohows.backend.admin.application.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.upload.excel.CoinExcelFormat;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Coin;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.CoinRepository;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CoinUploadService {

    private final ProjectRepository projectRepository;
    private final CoinRepository coinRepository;


    public Project findProject(String projectName) {
        if (!projectRepository.existsByName(projectName)) {
            throw new CryptoHowsException(projectName + "은 업로드 되지 않은 프로젝트 입니다.");
        }
        return projectRepository.findByNameIgnoreCase(projectName);
    }

    public void checkExistenceAndSave(Coin coin) {
        if (coinRepository.existsByCoinSymbol(coin.getCoinSymbol())) {
            throw new CryptoHowsException(coin.getCoinSymbol() + "은 이미 등록된 코인 심볼입니다.");
        }
        coinRepository.save(coin);
    }

    public void uploadCoins(MultipartFile file) {
        List<CoinExcelFormat> coinExcelFormats = CoinExcelFormat.toList(file);
        for (CoinExcelFormat coinExcelFormat : coinExcelFormats) {
            Project project = findProject(coinExcelFormat.getProject());
            Coin coin = coinExcelFormat.toCoin(project);
            checkExistenceAndSave(coin);
        }
    }
}
