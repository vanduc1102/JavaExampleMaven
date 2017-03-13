package my.example.pdf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

/**
 *
 * @author nvduc
 */
public class Main {
    public static void main(String[] args) throws IOException {
        File pdfFile = new File("./src/main/resources/Doc90.pdf");
        String background = "./src/main/resources/da.png";
//        String background = "./src/main/resources/t2i-background.png";
        Sign sign = new Sign(183,101,100,291,187,0,"1.33333333333333333333333333333","1.333333333333333333333333","2",1055,815,792,612);
        byte []pdfBtyes = PDFUtils.addImageToPdf(Files.readAllBytes(pdfFile.toPath()), ImageIO.read(new File(background)), sign);
        Files.write(Paths.get("target", "result.pdf"), pdfBtyes);
    }
}
