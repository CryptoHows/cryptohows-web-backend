package xyz.cryptohows.backend.admin.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.admin.application.upload.RoundUploadService;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.round.ui.dto.RoundResponse;
import xyz.cryptohows.backend.round.ui.dto.RoundSimpleResponse;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class RoundAdminService {

    private final RoundUploadService roundUploadService;
    private final RoundRepository roundRepository;

    public List<RoundSimpleResponse> findAll() {
        List<Round> rounds = roundRepository.findAllFetchJoinProject();
        return RoundSimpleResponse.toList(rounds);
    }

    public RoundResponse findById(Long roundId) {
        Round round = roundRepository.findRoundByIdFetchJoinProjectAndParticipants(roundId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 라운드는 없습니다."));
        return RoundResponse.of(round);
    }

    public void uploadExcel(MultipartFile file) {
        roundUploadService.uploadRounds(file);
    }

    public void deleteById(Long roundId) {
        Round round = roundRepository.findById(roundId)
                .orElseThrow(() -> new CryptoHowsException("해당 id의 라운드는 없습니다."));
        roundRepository.delete(round);
    }
}
