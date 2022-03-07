package xyz.cryptohows.backend.project.ui.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCountResponse {

    private final Long projects;

    public ProjectCountResponse(Long projects) {
        this.projects = projects;
    }
}
