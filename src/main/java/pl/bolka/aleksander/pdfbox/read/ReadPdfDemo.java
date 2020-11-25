package pl.bolka.aleksander.pdfbox.read;

import io.vavr.control.Try;
import java.io.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.text.PDFTextStripper;

public class ReadPdfDemo {

  public static void main(String[] args) {

    Try.run(() -> {
      File myFile = new File("src/main/resources/simple.pdf");

      PDDocument doc = PDDocument.load(myFile);

      PDFTextStripper stripper = new PDFTextStripper();
      String text = stripper.getText(doc);

      System.out.println(text);

      PDDocumentCatalog catalog = doc.getDocumentCatalog();
      PDMetadata metadata = catalog.getMetadata();

      if (metadata == null) {

        System.err.println("No metadata in document");
        System.exit(1);
      }

      try (InputStream is = metadata.createInputStream();
           InputStreamReader isr = new InputStreamReader(is);
           BufferedReader br = new BufferedReader(isr)) {

        br.lines().forEach(System.out::println);
      }

    }).onFailure(Throwable::printStackTrace);
  }

}
