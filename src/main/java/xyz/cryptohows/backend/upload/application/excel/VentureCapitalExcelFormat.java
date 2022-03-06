package xyz.cryptohows.backend.upload.application.excel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.vc.domain.VentureCapital;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VentureCapitalExcelFormat {

    private final String name;
    private final String about;
    private final String homepage;
    private final String logo;

    public VentureCapitalExcelFormat(String name, String about, String homepage, String logo) {
        this.name = name;
        this.about = about;
        this.homepage = homepage;
        this.logo = logo;
    }

    public static List<VentureCapitalExcelFormat> toList(MultipartFile file) {
        List<VentureCapitalExcelFormat> ventureCapitalExcelFormats = new ArrayList<>();

        Workbook workbook = ExcelFileUtil.toWorkbook(file);
        Sheet excelSheet = workbook.getSheetAt(0);
        for (int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            Row row = excelSheet.getRow(i);
            if (Objects.isNull(row)) {
                break;
            }
            VentureCapitalExcelFormat ventureCapitalExcelFormat = new VentureCapitalExcelFormat(
                    row.getCell(0).getStringCellValue(),
                    row.getCell(1).getStringCellValue(),
                    row.getCell(2).getStringCellValue(),
                    row.getCell(3).getStringCellValue()
            );
            ventureCapitalExcelFormats.add(ventureCapitalExcelFormat);
        }

        return ventureCapitalExcelFormats;
    }

    public VentureCapital toVentureCapital() {
        return VentureCapital.builder()
                .name(name)
                .about(about)
                .homepage(homepage)
                .logo(logo)
                .build();
    }
}
