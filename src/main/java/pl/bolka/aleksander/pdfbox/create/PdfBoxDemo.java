package pl.bolka.aleksander.pdfbox.create;

import io.vavr.control.Try;
import java.awt.Color;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class PdfBoxDemo {

  public static void main(String[] args) {
    createBasePdf();
  }

  private static void createBasePdf() {
    Try.run(() -> {
      PDDocument document = new PDDocument();
      PDPage page1 = new PDPage(PDRectangle.A4);

      PDRectangle rect = page1.getMediaBox();
      document.addPage(page1);

      PDFont fontPlain = PDType1Font.HELVETICA;
      PDFont fontBold = PDType1Font.HELVETICA_BOLD;
      PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
      PDFont fontMono = PDType1Font.COURIER;

      PDPageContentStream cos = new PDPageContentStream(document, page1);

      int line = 0;

      cos.beginText();
      cos.setFont(fontPlain, 12);
      cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
      cos.showText("Hello World");
      cos.endText();

      cos.beginText();
      cos.setFont(fontItalic, 12);
      cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
      cos.showText("Italic");
      cos.endText();

      cos.beginText();
      cos.setFont(fontBold, 12);
      cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
      cos.showText("Bold");
      cos.endText();

      cos.beginText();
      cos.setFont(fontMono, 12);
      cos.setNonStrokingColor(Color.BLUE);
      cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
      cos.showText("Monospaced blue");
      cos.endText();

      cos.close();

      PDPage page2 = new PDPage(PDRectangle.A4);
      document.addPage(page2);
      cos = new PDPageContentStream(document, page2);

      cos.setNonStrokingColor(Color.RED);
      cos.addRect(10, 10, 100, 100);
      cos.fill();

      cos.setLineWidth(1);
      cos.moveTo(200, 250);
      cos.lineTo(400, 250);
      cos.closeAndStroke();
      cos.setLineWidth(5);
      cos.moveTo(200, 300);
      cos.lineTo(400, 300);
      cos.closeAndStroke();

      PDPageContentStream finalCos = cos;
      String path = PdfBoxDemo.class.getClassLoader().getResource("kot.jpeg").getPath();
      Try.run(() -> {

        PDImageXObject ximage = PDImageXObject.createFromFile(path, document);
        float scale = 0.5f;
        finalCos.drawImage(ximage, 100, 400, ximage.getWidth() * scale, ximage.getHeight() * scale);
      }).onFailure(Throwable::printStackTrace);
      cos.close();

      String replace = path.replace("target/classes/kot.jpeg", "test.pdf");
      document.save(replace);
      document.close();
    }).onFailure(Throwable::printStackTrace);

  }

}
