package xyz.cryptohows.backend.upload.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.exception.CryptoHowsException;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.project.domain.repository.ProjectRepository;
import xyz.cryptohows.backend.round.domain.Round;
import xyz.cryptohows.backend.round.domain.RoundParticipation;
import xyz.cryptohows.backend.round.domain.repository.RoundParticipationRepository;
import xyz.cryptohows.backend.round.domain.repository.RoundRepository;
import xyz.cryptohows.backend.upload.application.excel.RoundExcelFormat;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class RoundUploadService {

    private final VentureCapitalRepository ventureCapitalRepository;
    private final ProjectRepository projectRepository;
    private final RoundRepository roundRepository;
    private final RoundParticipationRepository roundParticipationRepository;

    public void uploadRounds(MultipartFile file) {
        List<RoundExcelFormat> roundExcelFormats = RoundExcelFormat.toList(file);
        Map<Round, List<VentureCapital>> roundParticipants = new LinkedHashMap<>();
        for (RoundExcelFormat roundExcelFormat : roundExcelFormats) {
            Project project = findProject(roundExcelFormat.getProjectName());
            Round round = roundExcelFormat.toRound(project);
            roundRepository.save(round);
            List<VentureCapital> participants = findRoundParticipants(roundExcelFormat.getParticipants());
            roundParticipants.put(round, participants);
        }
        uploadRoundParticipation(roundParticipants);
    }

    private Project findProject(String projectName) {
        if (!projectRepository.existsByName(projectName)) {
            throw new CryptoHowsException(projectName + "은 업로드 되지 않은 프로젝트 입니다.");
        }
        return projectRepository.findByNameIgnoreCase(projectName);
    }

    private List<VentureCapital> findRoundParticipants(List<String> roundParticipants) {
        return ventureCapitalRepository.findAllByNameInIgnoreCase(roundParticipants);
    }

    private void uploadRoundParticipation(Map<Round, List<VentureCapital>> roundParticipants) {
        List<RoundParticipation> roundParticipations = new ArrayList<>();
        for (Map.Entry<Round, List<VentureCapital>> roundParticipant : roundParticipants.entrySet()) {
            Round round = roundParticipant.getKey();
            List<VentureCapital> participants = roundParticipant.getValue();
            for (VentureCapital participant : participants) {
                roundParticipations.add(new RoundParticipation(participant, round));
            }
        }
        roundParticipationRepository.saveAll(roundParticipations);
    }
}
