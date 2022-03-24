package xyz.cryptohows.backend.admin.application.upload.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.project.domain.Coin;
import xyz.cryptohows.backend.project.domain.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public class CoinExcelFormat {

    private final String project;
    private final String coinSymbol;
    private final String coinInformation;

    public CoinExcelFormat(String project, String coinSymbol, String coinInformation) {
        this.project = project;
        this.coinSymbol = coinSymbol;
        this.coinInformation = coinInformation;
    }

    public static List<CoinExcelFormat> toList(MultipartFile file) {
        List<CoinExcelFormat> coinExcelFormats = new ArrayList<>();

        Workbook workbook = ExcelFileUtil.toWorkbook(file);
        Sheet excelSheet = workbook.getSheetAt(0);
        for (int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            Row row = excelSheet.getRow(i);
            if (Objects.isNull(row) || Objects.isNull(row.getCell(0))
                    || "".equals(row.getCell(0).getStringCellValue().trim())) {
                break;
            }
            CoinExcelFormat coinExcelFormat = new CoinExcelFormat(
                    row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue(),
                    row.getCell(2).getStringCellValue()
            );
            coinExcelFormats.add(coinExcelFormat);
        }

        return coinExcelFormats;
    }

    public Coin toCoin(Project project) {
        return Coin.builder()
                .project(project)
                .coinSymbol(coinSymbol)
                .coinInformation(coinInformation)
                .build();
    }
}
