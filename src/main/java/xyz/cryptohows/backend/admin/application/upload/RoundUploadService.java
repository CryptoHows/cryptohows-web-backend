package xyz.cryptohows.backend.admin.application.upload;

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
import xyz.cryptohows.backend.admin.application.upload.excel.RoundExcelFormat;
import xyz.cryptohows.backend.vc.domain.VentureCapital;
import xyz.cryptohows.backend.vc.domain.repository.VentureCapitalRepository;

import javax.transaction.Transactional;
import java.util.*;

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
        for (RoundExcelFormat roundExcelFormat : roundExcelFormats) {
            Project project = findProject(roundExcelFormat.getProjectName());
            Round round = roundExcelFormat.toRound(project);
            validateRoundAndSave(project, round);
            uploadRoundParticipation(round, roundExcelFormat.getParticipants());
        }
    }

    public Project findProject(String projectName) {
        if (!projectRepository.existsByName(projectName)) {
            throw new CryptoHowsException(projectName + "은 업로드 되지 않은 프로젝트 입니다.");
        }
        return projectRepository.findByNameIgnoreCase(projectName);
    }

    public void validateRoundAndSave(Project project, Round round) {
        roundRepository.save(round);
        project.addRound(round);
        Set<Round> rounds = project.getRounds();
        for (Round projectRound : rounds) {
            if (!projectRound.equals(round) && projectRound.hasSameAttributes(round)) {
                throw new CryptoHowsException(project.getName() + round.getFundingStage() + "이 등록되어 있거나 파일 내 중복되어 있습니다.");
            }
        }
    }

    public void uploadRoundParticipation(Round round, List<String> roundParticipants) {
        List<VentureCapital> participants = ventureCapitalRepository.findAllByNameInIgnoreCase(roundParticipants);
        List<RoundParticipation> roundParticipations = new ArrayList<>();
        for (VentureCapital participant : participants) {
            roundParticipations.add(new RoundParticipation(participant, round));
        }
        roundParticipationRepository.saveAll(roundParticipations);
    }
}
