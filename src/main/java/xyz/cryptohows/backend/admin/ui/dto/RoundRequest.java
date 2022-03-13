package xyz.cryptohows.backend.admin.ui.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoundRequest {

    private String project;
    private String announcedDate;
    private String moneyRaised;
    private String newsArticle;
    private String fundingStage;
    private String participants;

    public RoundRequest(String project, String announcedDate, String moneyRaised, String newsArticle,
                        String fundingStage, String participants) {
        this.project = project;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.newsArticle = newsArticle;
        this.fundingStage = fundingStage;
        this.participants = participants;
    }

    public List<String> generateParticipants() {
        return Arrays.asList(participants.split(", "));
    }
}
