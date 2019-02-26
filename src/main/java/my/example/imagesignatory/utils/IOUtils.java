package my.example.imagesignatory.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.LoggerFactory;

/** @author nvduc */
public class IOUtils {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(IOUtils.class);
  private static final String JBOSS_TEMP_FOLDER_PATH = "jboss.server.temp.dir";
  private static final String PDF_EXTENSION = "pdf";

  private IOUtils() {}

  /**
   * This method is used to convert bufferedImage to Base64String
   *
   * @param bufferedImage Buffer of image file
   * @return String Base64String of image
   * @throws java.io.IOException
   */
  public static String convertToBase64(BufferedImage bufferedImage) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "jpg", baos);
    byte[] imageData = baos.toByteArray();
    return new String(Base64.encode(imageData));
  }

  /**
   * This method is used to convert Base64 to byte array
   *
   * @param base64 Base64String of image
   * @param fileType
   * @return byte[] Byte array
   * @throws java.io.IOException
   */
  public static byte[] convertToBytesFromBase64(String base64, String fileType) throws IOException {
    BufferedImage image2 = decodeToImage(base64);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image2, fileType, baos);
    return baos.toByteArray();
  }

  private static BufferedImage decodeToImage(String imageString) throws IOException {
    byte[] imageByte = Base64.decode(imageString);
    ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
    BufferedImage image = ImageIO.read(bis);
    bis.close();
    return image;
  }

  /**
   * Convert String data base64 to InputStream
   *
   * @param base64Str
   * @return
   */
  public static InputStream base64ToInputStream(String base64Str) {
    if (StringUtils.isEmpty(base64Str)) {
      return null;
    }
    byte[] bytes = Base64.decode(base64Str);
    InputStream is = new ByteArrayInputStream(bytes);
    return is;
  }

  public static void writeToDict(byte[] bytes, String fileName) {
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(new File(fileName));
      fos.write(bytes);
    } catch (IOException ex) {
      log.debug("writeToDict", ex);
    } finally {
      closeStream(fos);
    }
  }

  public static String writeImageToDisk(String imageBase64) {
    String systemTempPath = getTempFolder();

    byte dearr[] = Base64.decode(imageBase64);
    String fileType = getFileMimeType(dearr);
    String fileName = IOUtils.getRandomString().concat(".").concat(fileType);
    File file = new File(systemTempPath, fileName);
    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(file);
      fos.write(dearr);
      return file.getAbsolutePath();
    } catch (IOException ex) {
      log.debug("writeImageToDisk", ex);
    } finally {
      closeStream(fos);
    }
    return file.getAbsolutePath();
  }

  /**
   * Search file extension, if not found return "tmp"
   *
   * @param base64
   * @return
   */
  private static String getFileMimeType(byte[] byteArr) {
    InputStream is = new ByteArrayInputStream(byteArr);
    String mimeType;
    String fileExtension;
    try {
      mimeType =
          URLConnection.guessContentTypeFromStream(is); // mimeType is something like "image/jpeg"
      String delimiter = "[/]";
      String[] tokens = mimeType.split(delimiter);
      fileExtension = tokens[1];
    } catch (IOException ioException) {
      log.debug("getFileMimeType ", ioException);
      return "tmp";
    }
    return fileExtension;
  }

  public static String writeToDisk(InputStream inputStream, String fileName) throws IOException {
    String systemTempPath = getTempFolder();
    OutputStream outputStream = null;
    try {
      File outputFile = new File(systemTempPath, fileName);
      // write the inputStream to a FileOutputStream
      outputStream = new FileOutputStream(outputFile);
      int read;
      byte[] bytes = new byte[1024];
      while ((read = inputStream.read(bytes)) != -1) {
        outputStream.write(bytes, 0, read);
      }
      return outputFile.getAbsolutePath();
    } catch (IOException ex) {
      log.error("writeToDisk : ", ex);
      throw new IOException(ex.getMessage());
    } finally {
      closeStream(inputStream);
      closeStream(outputStream);
    }
  }

  public static void copyStream(InputStream is, OutputStream os) throws IOException {
    byte[] buffer = new byte[1024];
    int c;
    while ((c = is.read(buffer)) != -1) {
      os.write(buffer, 0, c);
    }
  }

  public static void closeStream(Closeable is) {
    if (is != null) {
      try {
        is.close();
      } catch (IOException e) {
        log.error("failure", e);
      }
    }
  }

  private static String getRandomString() {
    return UUID.randomUUID().toString().replace("-", "");
  }

  public static boolean removeFile(String fileName) {
    if (StringUtils.isEmpty(fileName)) {
      return false;
    }
    try {
      File file = new File(fileName);
      if (file.exists()) {
        return file.delete();
      }
    } catch (Exception ex) {
      log.debug("Can not remove file " + fileName + " ", ex);
    }
    return false;
  }

  public static boolean removeFile(File file) {
    if (file == null) {
      return false;
    }
    try {
      if (file.exists()) {
        return file.delete();
      }
    } catch (Exception ex) {
      log.debug("Can not remove file " + file.getAbsolutePath() + " ", ex);
    }
    return false;
  }

  public static boolean isFileExist(String filePath) {
    if (StringUtils.isEmpty(filePath)) {
      return false;
    }
    File file = new File(filePath);
    return file.exists();
  }

  public static String getAbsoluteFilePath(String filename) {
    String systemTempPath = getTempFolder();
    File outputFile = new File(systemTempPath, filename);
    return outputFile.getAbsolutePath();
  }

  private static String getTempFolder() {
    return StringUtils.isNotBlank(System.getProperty(JBOSS_TEMP_FOLDER_PATH))
        ? System.getProperty(JBOSS_TEMP_FOLDER_PATH)
        : System.getProperty("java.io.tmpdir");
  }

  /**
   * Create temporary file in JBOSS_TEMP_FOLDER
   *
   * @param prefixName Prefix name of the temporary file. Example readyToSign.
   * @param extention Extension file. Example .PDF, .DOC ...
   * @return
   */
  public static String getTemporyFilePath(String prefixName, String extention) {
    return getTemporyFile(prefixName, extention).getAbsolutePath();
  }

  public static File getTemporyFile(String prefixName, String extention) {
    String systemTempPath = getTempFolder();
    return new File(systemTempPath, prefixName + getRandomString() + extention);
  }

  public static String getTemporyFilePath() {
    String systemTempPath = getTempFolder();
    File outputFile = new File(systemTempPath, getRandomString() + ".tmp");
    return outputFile.getAbsolutePath();
  }

  public static String getTemporyFilePath(String postFix) {
    return getTemporyFilePath(StringUtils.EMPTY, postFix);
  }

  public static File fileWithoutDuplicateName(String exportFolder, String fileName) {
    exportFolder += File.separator;
    String suffix = "({suffix})";
    String finalFileName;
    fileName = fileName.replace(".pdf", "");
    int index = 0;
    File file = new File(exportFolder + fileName + ".pdf");
    while (file.exists() && !file.isDirectory()) {
      index++;
      finalFileName = exportFolder + fileName + suffix.replace("{suffix}", index + "") + ".pdf";
      file = new File(finalFileName);
    }
    file.getParentFile().mkdirs();
    return file;
  }

  /**
   * Read the file and calculate the SHA-1 checksum
   *
   * @param input
   * @return the hex representation of the SHA-1 using uppercase chars
   */
  public static String createSHA1(InputStream input) {
    try {

      MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
      byte[] buffer = new byte[8192];
      int len = input.read(buffer);
      while (len != -1) {
        sha1.update(buffer, 0, len);
        len = input.read(buffer);
      }
      return new HexBinaryAdapter().marshal(sha1.digest());
    } catch (NoSuchAlgorithmException | IOException ex) {
      log.debug("createSHA1 ", ex);
    } finally {
      closeStream(input);
    }
    return "";
  }

  public static Boolean isPdfFile(File file) {

    String fileExtension = FilenameUtils.getExtension(file.getName());
    return file.isFile() && PDF_EXTENSION.equalsIgnoreCase(fileExtension);
  }

  public static String encodeFileToBase64Binary(File file) throws IOException {
    return Base64.toBase64String(FileUtils.readFileToByteArray(file));
  }
}
