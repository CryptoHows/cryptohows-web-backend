package xyz.cryptohows.backend.upload.application.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.project.domain.Category;
import xyz.cryptohows.backend.project.domain.Mainnet;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static xyz.cryptohows.backend.upload.application.excel.ExcelFileUtil.checkNullAndGetStringCellValue;

@Getter
public class ProjectExcelFormat {

    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;
    private final String category;
    private final String mainnet;
    private final List<String> investors;

    public ProjectExcelFormat(String name, String about, String homepage, String logo, String category, String mainnet,
                              List<String> investors) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
        this.category = category;
        this.mainnet = mainnet;
        this.investors = investors;
    }

    public static List<ProjectExcelFormat> toList(MultipartFile file) {
        List<ProjectExcelFormat> projectExcelFormats = new ArrayList<>();

        Workbook workbook = ExcelFileUtil.toWorkbook(file);
        Sheet excelSheet = workbook.getSheetAt(0);
        for (int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            Row row = excelSheet.getRow(i);
            if (Objects.isNull(row) || Objects.isNull(row.getCell(0))
                    || "".equals(row.getCell(0).getStringCellValue().trim())) {
                break;
            }
            ProjectExcelFormat projectExcelFormat = new ProjectExcelFormat(
                    row.getCell(0).getStringCellValue(),
                    checkNullAndGetStringCellValue(row.getCell(1)),
                    checkNullAndGetStringCellValue(row.getCell(2)),
                    checkNullAndGetStringCellValue(row.getCell(3)),
                    checkNullAndGetStringCellValue(row.getCell(4)),
                    checkNullAndGetStringCellValue(row.getCell(5)),
                    Arrays.asList(row.getCell(6).getStringCellValue().split(", "))
            );
            projectExcelFormats.add(projectExcelFormat);
        }

        return projectExcelFormats;
    }

    public Project toProject() {
        return Project.builder()
                .name(name)
                .about(about)
                .homepage(homepage)
                .logo(logo)
                .category(Category.of(category))
                .mainnet(Mainnet.of(mainnet))
                .build();
    }
}
