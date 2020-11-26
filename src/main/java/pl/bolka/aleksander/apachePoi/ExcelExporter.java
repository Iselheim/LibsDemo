package pl.bolka.aleksander.apachePoi;

import io.vavr.control.Try;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

  public void generateReport(Table table) {
    Workbook workbook = new XSSFWorkbook();
    Sheet names = workbook.createSheet("Personel");
    createHeaderRow(names, table.getHeaders());
    AtomicInteger counter = new AtomicInteger(1);
    table.getTableRows()
         .forEach(tableRow -> createRow(names, tableRow, counter.addAndGet(1)));

    Sheet generatedBy = workbook.createSheet("Wygenerowane przez");
    createHeaderRow(generatedBy,
        Arrays.asList(
            Header.builder()
                  .fontSize(20)
                  .isBold(true)
                  .name("Wygenerowe przez")
                  .build(),
            Header.builder()
                  .fontSize(20)
                  .isBold(true)
                  .name("Data")
                  .build())
    );
    Row row = generatedBy.createRow(1);

    CellStyle cellStyle = generatedBy.getWorkbook().createCellStyle();
    Font font = generatedBy.getWorkbook().createFont();
    font.setFontHeightInPoints((short) 12);
    cellStyle.setFont(font);

    Cell firstName = row.createCell(0);
    firstName.setCellStyle(cellStyle);
    firstName.setCellValue("SuperReport sp z.o.o");

    Cell lastName = row.createCell(1);
    lastName.setCellStyle(cellStyle);
    lastName.setCellValue(LocalDateTime.now().toString());

    Try.run(() -> {
      File file = new File("names.xlsx");
      file.createNewFile();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      workbook.write(fileOutputStream);
    }).onFailure(Throwable::printStackTrace);
  }

  private void createRow(Sheet sheet, TableRow tableRow, int index) {

    Row row = sheet.createRow(index);

    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
    Font font = sheet.getWorkbook().createFont();
    font.setFontHeightInPoints((short) 12);
    cellStyle.setFont(font);

    Cell firstName = row.createCell(0);
    firstName.setCellStyle(cellStyle);
    firstName.setCellValue(tableRow.getFirstName());

    Cell lastName = row.createCell(1);
    lastName.setCellStyle(cellStyle);
    lastName.setCellValue(tableRow.getLastName());

    Cell age = row.createCell(2);
    age.setCellStyle(cellStyle);
    age.setCellValue(tableRow.getAge());
  }

  private void createHeaderRow(Sheet sheet, List<Header> headerList) {

    Row row = sheet.createRow(0);
    AtomicInteger cellCounter = new AtomicInteger(0);
    headerList.forEach(header -> {
      CellStyle cellStyle = getCellStyle(sheet, header);
      Cell cellTitle = row.createCell(cellCounter.getAndAdd(1));
      cellTitle.setCellStyle(cellStyle);
      cellTitle.setCellValue(header.getName());
    });
  }

  private CellStyle getCellStyle(Sheet sheet, Header header) {
    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
    Font font = sheet.getWorkbook().createFont();
    font.setFontHeightInPoints((short) header.getFontSize());
    font.setBold(header.isBold);
    cellStyle.setFont(font);
    return cellStyle;
  }

  @Getter
  @Setter
  @Builder
  public static class Header {

    private String name;

    private int fontSize;

    private boolean isBold;
  }

  @Getter
  @Setter
  @Builder
  public static class TableRow {

    private String firstName;

    private String lastName;

    private int age;

  }

  @Getter
  @Setter
  public static class Table {

    private List<Header> headers = new ArrayList<>();

    private List<TableRow> tableRows = new ArrayList<>();

  }
}


