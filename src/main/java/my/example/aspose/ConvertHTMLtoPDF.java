package my.example.aspose;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author nvduc
 */
public class ConvertHTMLtoPDF {

    public static void checkCurrentPath() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
    }

    public static void main(String args[]) {
        checkCurrentPath();
//        String basePath = "./src/main/resources/email-hule.html";
//        String basePath = "./src/main/resources/email-inlined.html";
        String basePath = "./src/main/resources/simple.html";
        com.aspose.pdf.HtmlLoadOptions htmloptions = new com.aspose.pdf.HtmlLoadOptions(basePath);
        com.aspose.pdf.Document doc = new com.aspose.pdf.Document(basePath, htmloptions);
        doc.save("./target/hele.pdf");
    }
}
