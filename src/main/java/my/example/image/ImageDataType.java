package my.example.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.apache.commons.codec.binary.Base64;

/** @author ducnguyen */
public class ImageDataType {

  public static void main(String[] args) throws IOException {
    String imagePath = "./src/main/resources/chelsea.png";
    //        String imagePath = "./src/main/resources/anh14.jpg";

    String sampleBasic64Image = encodeFileToBase64Binary(imagePath);
    System.out.println("Image to base64 : " + sampleBasic64Image);
    BufferedImage image = getImage(sampleBasic64Image);
    String mime = getImageFileType(sampleBasic64Image);
    Path outputFile =
        Files.createFile(
            Paths.get(".", "target", String.valueOf(System.currentTimeMillis()) + "." + mime));
    ImageIO.write(image, mime, outputFile.toFile());
  }

  private static String getImageFileType(String base64) throws IOException {
    byte[] imageByteArray = Base64.decodeBase64(base64.getBytes());
    InputStream is = new ByteArrayInputStream(imageByteArray);
    // Find out image type
    String mimeType;
    String fileExtension = null;
    try {
      mimeType =
          URLConnection.guessContentTypeFromStream(is); // mimeType is something like "image/jpeg"
      String delimiter = "[/]";
      String[] tokens = mimeType.split(delimiter);
      fileExtension = tokens[1];
    } catch (IOException ioException) {

    }
    System.out.println("my.example.image.ImageDataType.getImageFileType() : " + fileExtension);
    return fileExtension;
  }

  private static BufferedImage getImage(String imageBase64) throws IOException {
    BufferedImage image;
    byte[] imageByte;

    imageByte = Base64.decodeBase64(imageBase64.getBytes());
    try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
      image = ImageIO.read(bis);
    }
    return image;
  }

  private static String encodeFileToBase64Binary(String fileName) throws IOException {

    File file = new File(fileName);
    byte[] bytes = loadFile(file);
    byte[] encoded = Base64.encodeBase64(bytes);
    String encodedString = new String(encoded);

    return encodedString;
  }

  private static byte[] loadFile(File file) throws IOException {
    InputStream is = new FileInputStream(file);

    long length = file.length();
    if (length > Integer.MAX_VALUE) {
      // File is too large
    }
    byte[] bytes = new byte[(int) length];

    int offset = 0;
    int numRead = 0;
    while (offset < bytes.length
        && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
      offset += numRead;
    }

    if (offset < bytes.length) {
      throw new IOException("Could not completely read file " + file.getName());
    }

    is.close();
    return bytes;
  }
}
