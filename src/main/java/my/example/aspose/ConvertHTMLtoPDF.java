package my.example.aspose;

import com.aspose.pdf.Document;
import com.aspose.pdf.HtmlLoadOptions;
import com.aspose.pdf.License;
import java.nio.file.Path;
import java.nio.file.Paths;

/** @author nvduc */
public class ConvertHTMLtoPDF {

  public static void checkCurrentPath() {
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
    System.out.println("Current relative path is: " + s);
  }

  public static void main(String args[]) throws Exception {
    checkCurrentPath();
    //        String basePath = "./src/main/resources/email-hule.html";
    //        String basePath = "./src/main/resources/email-inlined.html";
    // String basePath = ""./src/main/resources/simple.html";
    License license = new License();
    license.setLicense(".\\src\\main\\resources\\Aspose.Pdf.lic");
    String basePath = "./src/main/resources/email-inlined.html";
    HtmlLoadOptions htmloptions = new HtmlLoadOptions(basePath);
    Document doc = new Document(basePath, htmloptions);
    doc.save(".\\target\\hele.pdf");
  }
}
