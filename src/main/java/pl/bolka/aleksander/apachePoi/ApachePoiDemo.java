package pl.bolka.aleksander.apachePoi;

import io.vavr.control.Try;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import pl.bolka.aleksander.pdfbox.create.PdfBoxDemo;

public class ApachePoiDemo {

  public static void main(String[] args) {
    ApachePoiDemo apachePoiDemo = new ApachePoiDemo();
    apachePoiDemo.baseCreateXls();

//    apachePoiDemo.moreObjectDrivenXls();

//    readFromFile();

  }

  private static void readFromFile() {
    Try.run(() -> {
      FileInputStream file = new FileInputStream(new File(PdfBoxDemo.class.getClassLoader().getResource("task.xlsx").getPath()));
      Workbook workbook = new XSSFWorkbook(file);
      Sheet sheet = workbook.getSheetAt(0);

      Map<Integer, List<String>> data = new HashMap<>();

      int i = 0;
      for (Row row : sheet) {
        data.put(i, new ArrayList<String>());
        for (Cell cell : row) {
          switch (cell.getCellTypeEnum()) {
            case STRING:
              data.get(i).add(cell.getStringCellValue());
              break;
            case NUMERIC:
              data.get(i).add(cell.getNumericCellValue() + "");
              break;
            case BOOLEAN:
              data.get(i).add(cell.getBooleanCellValue() + "");
              break;
            case FORMULA:
              data.get(i).add(cell.getCellFormula());
              break;
            default: data.get(i).add(" ");
          }
        }
        i++;
      }
      System.out.println(data);
    }).onFailure(Throwable::printStackTrace);
  }

  private void moreObjectDrivenXls() {
    ExcelExporter excelExporter = new ExcelExporter();

    ExcelExporter.Table table = new ExcelExporter.Table();

    List<ExcelExporter.Header> headers = Arrays.asList(
        ExcelExporter.Header.builder()
                            .name("Imię")
                            .isBold(true)
                            .fontSize(20)
                            .build(),
        ExcelExporter.Header.builder()
                            .name("Nazwisko")
                            .fontSize(20)
                            .isBold(false)
                            .build(),
        ExcelExporter.Header.builder()
                            .name("Wiek")
                            .fontSize(20)
                            .isBold(false)
                            .build()
    );
    table.getHeaders().addAll(headers);

    List<ExcelExporter.TableRow> tableRows = Arrays.asList(
        ExcelExporter.TableRow.builder()
                              .firstName("Jan")
                              .lastName("Kowalski")
                              .age(40)
                              .build(),
        ExcelExporter.TableRow.builder()
                              .firstName("Janusz")
                              .lastName("Tracz")
                              .age(36)
                              .build(),
        ExcelExporter.TableRow.builder()
                              .firstName("Jonny")
                              .lastName("Bravo")
                              .age(42)
                              .build(),
        ExcelExporter.TableRow.builder()
                              .firstName("Halina")
                              .lastName("Kiepska")
                              .age(35)
                              .build()
    );
    table.getTableRows().addAll(tableRows);

    excelExporter.generateReport(table);
  }

  private void baseCreateXls() {
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Personel");

    Object[][] bookData = {
        {"Jan", "Kowalski", 40},
        {"Janusz", "Tracz", 36},
        {"Jonny", "Bravo", 42},
        {"Halina", "Kiepska", 35},
    };

    int rowCount = 0;

    for (Object[] aBook : bookData) {
      Row row = sheet.createRow(rowCount++);

      int columnCount = 0;

      for (Object field : aBook) {
        Cell cell = row.createCell(columnCount++);
        if (field instanceof String) {
          cell.setCellValue((String) field);
        } else if (field instanceof Integer) {
          cell.setCellValue((Integer) field);
        }
      }

    }

    Try.run(() -> {
      File file = new File("test.xlsx");
      file.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      workbook.write(fileOutputStream);
    }).onFailure(Throwable::printStackTrace);
  }

}
