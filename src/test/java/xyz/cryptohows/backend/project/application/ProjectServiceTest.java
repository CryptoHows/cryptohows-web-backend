package xyz.cryptohows.backend.project.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.cryptohows.backend.project.ui.dto.ProjectPageResponse;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectServiceTest extends ProjectServiceTestFixture {

    @Autowired
    private ProjectService projectService;

    @DisplayName("mainnet을 기반으로 프로젝트를 찾을 수 있다. (ID 큰 순서부터 반환)")
    @Test
    void findProjectByMainnet() {
        // given
        String mainnet = "terra, solana";
        String category = "";

        // when
        ProjectPageResponse response = projectService.findProjects(mainnet, category, 0, 10);

        // then
        assertThat(response.getProjects()).hasSize(2);
        assertThat(response.getProjects().get(0).getId()).isEqualTo(LUNA.getId());
        assertThat(response.getProjects().get(1).getId()).isEqualTo(SOLANA.getId());
        assertThat(response.getTotalProjects()).isEqualTo(2L);
    }

    @DisplayName("category를 기반으로 프로젝트를 찾을 수 있다. (ID 큰 순서부터 반환)")
    @Test
    void findProjectByCategory() {
        // given
        String mainnet = "";
        String category = "infrastructure";

        // when
        ProjectPageResponse response = projectService.findProjects(mainnet, category, 0, 10);

        // then
        assertThat(response.getProjects()).hasSize(2);
        assertThat(response.getProjects().get(0).getId()).isEqualTo(LUNA.getId());
        assertThat(response.getProjects().get(1).getId()).isEqualTo(SOLANA.getId());
        assertThat(response.getTotalProjects()).isEqualTo(2L);
    }

    @DisplayName("여러 category를 기반으로 프로젝트를 찾을 수 있다. (ID 큰 순서부터 반환)")
    @Test
    void findProjectByCategories() {
        // given
        String mainnet = "";
        String category = "infrastructure, web3";

        // when
        ProjectPageResponse response = projectService.findProjects(mainnet, category, 0, 10);

        // then
        assertThat(response.getProjects()).hasSize(3);
        assertThat(response.getProjects().get(0).getId()).isEqualTo(axieInfinity.getId());
        assertThat(response.getProjects().get(1).getId()).isEqualTo(LUNA.getId());
        assertThat(response.getProjects().get(2).getId()).isEqualTo(SOLANA.getId());
        assertThat(response.getTotalProjects()).isEqualTo(3L);
    }

    @DisplayName("mainnet과 category를 기반으로 프로젝트를 찾을 수 있다. (ID 큰 순서부터 반환)")
    @Test
    void findProjectByMainnetAndCategory() {
        // given
        String mainnet = "terra";
        String category = "infrastructure, web3";

        // when
        ProjectPageResponse response = projectService.findProjects(mainnet, category, 0, 10);

        // then
        assertThat(response.getProjects()).hasSize(1);
        assertThat(response.getProjects().get(0).getId()).isEqualTo(LUNA.getId());
        assertThat(response.getTotalProjects()).isEqualTo(1L);
    }

    @DisplayName("라운드에 등록된 프로젝트를 찾을 수 있다. (ID 큰 순서부터 반환)")
    @Test
    void findProject() {
        // given
        String mainnet = "";
        String category = "";

        // when
        ProjectPageResponse response = projectService.findProjects(mainnet, category, 0, 10);

        // then
        assertThat(response.getProjects()).hasSize(3);
        assertThat(response.getProjects().get(0).getId()).isEqualTo(axieInfinity.getId());
        assertThat(response.getProjects().get(1).getId()).isEqualTo(LUNA.getId());
        assertThat(response.getProjects().get(2).getId()).isEqualTo(SOLANA.getId());
        assertThat(response.getTotalProjects()).isEqualTo(3L);
    }
}
