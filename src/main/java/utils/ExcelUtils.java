package utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtils {

    private static final String FILE_PATH =
            System.getProperty("user.dir") + "/src/test/resources/testData/TestOutput.xlsx";

    public static void writeBookshelfData(List<String> names, List<String> prices) {

        try {
            XSSFWorkbook workbook = getWorkbook();
            Sheet sheet = workbook.getSheet("Bookshelves");

            int rowNum = sheet.getLastRowNum() + 1;

            for (int i = 0; i < names.size(); i++) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(rowNum - 1);
                row.createCell(1).setCellValue(names.get(i));
                row.createCell(2).setCellValue(prices.get(i));
            }

            saveWorkbook(workbook);

            LoggerManager.info("Bookshelf data written successfully");

        } catch (Exception e) {
            LoggerManager.error("Excel writing failed: " + e.getMessage());
        }
    }

    public static void writeTerraData(List<String> items) {

        try {
            XSSFWorkbook workbook = getWorkbook();
            Sheet sheet = workbook.getSheet("Terra Collection");

            int rowNum = sheet.getLastRowNum() + 1;

            for (String item : items) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue("Terra Collection");
                row.createCell(1).setCellValue(item);
            }

            saveWorkbook(workbook);

            LoggerManager.info("Terra data written successfully");

        } catch (Exception e) {
            LoggerManager.error("Excel writing failed: " + e.getMessage());
        }
    }

    private static XSSFWorkbook getWorkbook() throws Exception {
        FileInputStream fis = new FileInputStream(FILE_PATH);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        fis.close();
        return workbook;
    }

    private static void saveWorkbook(XSSFWorkbook workbook) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH)) {
            workbook.write(fos);
        }
        workbook.close();
    }
}