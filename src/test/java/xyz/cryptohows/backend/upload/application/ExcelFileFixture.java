package xyz.cryptohows.backend.upload.application;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;

public class ExcelFileFixture {

    public static final String FILE_PATH = "/src/test/resources/static/";
    public static final String VENTURE_CAPITALS = "VC_excel_format.xlsx";
    public static final String PROJECTS = "Project_excel_format.xlsx";
    public static final String ROUNDS = "Round_excel_format.xlsx";
    public static final String VENTURE_CAPITALS_DUPLICATED = "VC_duplicated.xlsx";
    public static final String PROJECTS_DUPLICATED = "Project_duplicated.xlsx";
    public static final String ROUNDS_PROJECT_NOT_UPLOADED = "Round_project_not_uploaded.xlsx";

    public static MultipartFile getVentureCapitalsFile() {
        File ventureCapitals = new File(new File("").getAbsolutePath() + FILE_PATH + VENTURE_CAPITALS);
        return generateMultipartFile(VENTURE_CAPITALS, ventureCapitals);
    }

    public static MultipartFile getProjects() {
        File projects = new File(new File("").getAbsolutePath() + FILE_PATH + PROJECTS);
        return generateMultipartFile(PROJECTS, projects);
    }

    public static MultipartFile getRounds() {
        File rounds = new File(new File("").getAbsolutePath() + FILE_PATH + ROUNDS);
        return generateMultipartFile(ROUNDS, rounds);
    }

    public static MultipartFile getVentureCapitalsDuplicatedFile() {
        File ventureCapitals = new File(new File("").getAbsolutePath() + FILE_PATH + VENTURE_CAPITALS_DUPLICATED);
        return generateMultipartFile(VENTURE_CAPITALS, ventureCapitals);
    }

    public static MultipartFile getProjectsDuplicatedFile() {
        File projects = new File(new File("").getAbsolutePath() + FILE_PATH + PROJECTS_DUPLICATED);
        return generateMultipartFile(PROJECTS, projects);
    }

    public static MultipartFile getRoundsProjectNotUploadedFile() {
        File rounds = new File(new File("").getAbsolutePath() + FILE_PATH + ROUNDS_PROJECT_NOT_UPLOADED);
        return generateMultipartFile(ROUNDS, rounds);
    }

    private static MultipartFile generateMultipartFile(String fileName, File file) {
        try {
            FileItem fileItem = new DiskFileItem(fileName, Files.probeContentType(file.toPath()), false,
                    file.getName(), (int) file.length(), file.getParentFile());
            InputStream input = new FileInputStream(file);
            OutputStream os = fileItem.getOutputStream();
            IOUtils.copy(input, os);
            return new CommonsMultipartFile(fileItem);
        } catch (IOException e) {
            throw new IllegalStateException("멀티파트 파일 생성 실패");
        }
    }
}
