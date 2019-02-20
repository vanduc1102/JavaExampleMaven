package my.example.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import my.example.image.ImageUtils;
import my.example.imagesignatory.utils.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PDFUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(PDFUtils.class);

  public static final String DEFAULT_THUMBNAIL =
      "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";

  private PDFUtils() {}

  public static ByteArrayOutputStream standardizePDFFile(
      ByteBuffer buffer, int startPage, int endPage) throws Exception {
    // Get first page
    PdfReader pdfReader = new PdfReader(buffer.array());
    ByteArrayOutputStream arrayOutputStreamOriginal = new ByteArrayOutputStream();
    Document document = new Document();
    PdfCopy copy = new PdfCopy(document, arrayOutputStreamOriginal);
    document.open();
    for (int index = startPage; index <= endPage; index++) {
      copy.addPage(copy.getImportedPage(pdfReader, index));
    }
    document.close();
    pdfReader = new PdfReader(arrayOutputStreamOriginal.toByteArray());
    ByteArrayOutputStream arrayOutputStreamConverted = new ByteArrayOutputStream();
    PdfStamper pdfStamper = new PdfStamper(pdfReader, arrayOutputStreamConverted);
    pdfStamper.getWriter().setPdfVersion(PdfWriter.PDF_VERSION_1_7);
    pdfStamper.close();
    document.close();
    return arrayOutputStreamConverted;
  }

  public static int getPageRotation(byte[] pdfData, int page) {
    PDDocument document = null;
    try {
      document = PDDocument.load(new ByteArrayInputStream(pdfData));
      PDPage pageObject = document.getPage(page);
      return pageObject.getRotation();
    } catch (IOException ex) {
      LOGGER.debug("Can not load the PDF file", ex);
    } finally {
      IOUtils.closeStream(document);
    }
    return 0;
  }

  public static byte[] addImageToPdf(byte[] pdfData, BufferedImage image, Sign sign)
      throws IOException {
    PDDocument doc = null;
    PDPageContentStream contentStream = null;
    File imageFile = null;
    try {
      doc = PDDocument.load(new ByteArrayInputStream(pdfData));
      PDPage page = doc.getPage(sign.getPageNumber());

      int degree = page.getRotation() % 360;

      String imagePath = IOUtils.getTemporyFilePath(".png");
      imageFile = new File(imagePath);
      ImageIO.write(image, "png", imageFile);

      PDImageXObject pdImage = PDImageXObject.createFromFileByContent(imageFile, doc);
      contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true);
      float pageWidth = page.getMediaBox().getWidth();
      float pageHeight = page.getMediaBox().getHeight();
      float[] dementions = getImagePositionPdfBox(sign, degree);
      float x = dementions[0];
      float y = dementions[1];
      float correctX = x;
      float correctY = y;
      float imageWidth = dementions[2];
      float imageHeight = dementions[3];
      switch (degree) {
        case 0:
          correctX = x;
          correctY = pageHeight - y - imageHeight;
          break;
        case 90:
          correctX = y;
          correctY = x;
          imageFile = ImageUtils.rotateImage(image, -90);
          pdImage = PDImageXObject.createFromFileByContent(imageFile, doc);
          imageWidth = dementions[3];
          imageHeight = dementions[2];
          break;
        case 180:
          correctX = pageWidth - x - imageWidth;
          correctY = y;
          imageFile = ImageUtils.rotateImage(image, -180);
          pdImage = PDImageXObject.createFromFileByContent(imageFile, doc);
          imageWidth = dementions[2];
          imageHeight = dementions[3];
          break;
        case 270:
          correctX = pageWidth - imageHeight - y;
          correctY = pageHeight - imageWidth - x;
          imageFile = ImageUtils.rotateImage(image, -270);
          pdImage = PDImageXObject.createFromFileByContent(imageFile, doc);
          imageWidth = dementions[3];
          imageHeight = dementions[2];
          break;
        default:
          break;
      }
      BufferedImage bi = ImageIO.read(imageFile);
      float[] demension =
          getCorrectImagePosition(
              correctX, correctY, imageWidth, imageHeight, bi.getWidth(), bi.getHeight());
      contentStream.drawImage(pdImage, demension[0], demension[1], demension[2], demension[3]);
      contentStream.close();

      ByteArrayOutputStream outStream = new ByteArrayOutputStream();
      doc.save(outStream);
      return outStream.toByteArray();

    } finally {
      IOUtils.removeFile(imageFile);
    }
  }

  /**
   * This method get convert position of dsbSign to position of PDF with coordinate from bottom-left
   *
   * @param dsbSign
   * @param doc
   * @return
   */
  public static float[] getSuisseIdSignaturePosition(Sign dsbSign, byte[] doc) {
    float[] rect = new float[] {0, 0, 0, 0};
    if (dsbSign.getWidth() == null
        || dsbSign.getHeight() == null
        || dsbSign.getX() == null
        || dsbSign.getY() == null) {
      return rect;
    }

    int rotation = PDFUtils.getPageRotation(doc, dsbSign.getPageNumber()) % 360;
    int originalH = dsbSign.getOriginalPageHeight();
    int originalW = dsbSign.getOriginalPageWidth();
    int currentH = dsbSign.getCurrentPageHeight();
    int currentW = dsbSign.getCurrentPageWidth();

    switch (rotation) {
      case 90:
      case 270:
        int tmp = originalH;
        originalH = originalW;
        originalW = tmp;
        break;
      default:
        break;
        // Implement in a similar fashion
    }

    float rateh = (float) (1.0 * originalH / currentH);
    float ratew = (float) (1.0 * originalW / currentW);
    float x = dsbSign.getX() * ratew;
    float y = dsbSign.getY() * rateh;
    float w = (dsbSign.getWidth() + 20) * ratew;
    float h = dsbSign.getHeight() * rateh;
    if (dsbSign.getRatio() != null) {
      try {
        h = w / Float.parseFloat(dsbSign.getRatio());
      } catch (NumberFormatException ex) {
        LOGGER.debug("getSignaturePosition ", ex);
      }
    }
    float x1 = x;
    float y1 = originalH - y - h;
    float x2 = x + w;
    float y2 = originalH - y;
    rect[0] = x1;
    rect[1] = y1;
    rect[2] = x2;
    rect[3] = y2;
    return rect;
  }

  /**
   * This method get convert position of dsbSign to position of PDF with coordinate from bottom-left
   *
   * @param dsbSign
   * @param path
   * @return
   */
  private static float[] getCorrectImagePosition(
      float currentX,
      float currentY,
      float rectangleWidth,
      float rectangleHeight,
      float imageWidth,
      float imageHeight) {
    float[] demension = new float[] {0, 0, 0, 0};
    float imageRatio = (float) imageHeight / imageWidth;
    float rectangleRatio = (float) rectangleHeight / rectangleWidth;
    float x, y, height, width;
    x = currentX;
    y = currentY;
    if (Math.abs(imageRatio - rectangleRatio) < 0.05) {
      height = rectangleHeight;
      width = rectangleWidth;
    } else {
      float hRatio = (float) rectangleHeight / imageHeight;
      float wRatio = (float) rectangleWidth / imageWidth;
      width = (int) (hRatio * imageWidth);
      if (width > rectangleWidth) {
        height = (int) (imageHeight * wRatio);
        width = rectangleWidth;
        y += (rectangleHeight - height) / 2;
      } else {
        height = rectangleHeight;
        x += (rectangleWidth - width) / 2;
      }
    }
    demension[0] = x;
    demension[1] = y;
    demension[2] = width;
    demension[3] = height;
    return demension;
  }

  /**
   * This method get convert position of dsbSign to position of PDFBox with coordinate from
   * bottom-left
   *
   * @param dsbSign
   * @param degree
   * @return
   */
  public static float[] getImagePositionPdfBox(Sign dsbSign, int degree) {
    float[] rect = new float[] {0, 0, 0, 0};
    if (dsbSign.getWidth() == null
        || dsbSign.getHeight() == null
        || dsbSign.getX() == null
        || dsbSign.getY() == null) {
      return rect;
    }

    int originalH = dsbSign.getOriginalPageHeight();
    int originalW = dsbSign.getOriginalPageWidth();
    int currentH = dsbSign.getCurrentPageHeight();
    int currentW = dsbSign.getCurrentPageWidth();

    switch (degree) {
      case 90:
      case 270:
        int tmp = originalH;
        originalH = originalW;
        originalW = tmp;
        break;
      default:
        break;
        // Implement in a similar fashion
    }
    float rateh = (float) (1.0 * originalH / currentH);
    float ratew = (float) (1.0 * originalW / currentW);
    float x = dsbSign.getX() * ratew;
    float y = dsbSign.getY() * rateh;
    float w = (dsbSign.getWidth() + 20) * ratew;
    float h = dsbSign.getHeight() * rateh;
    if (dsbSign.getRatio() != null) {
      try {
        h = w / Float.parseFloat(dsbSign.getRatio());
      } catch (NumberFormatException ex) {
        LOGGER.debug("getSignaturePosition ", ex);
      }
    }
    float x1 = x;
    float y1 = y;
    rect[0] = x1;
    rect[1] = y1;
    rect[2] = w;
    rect[3] = h;
    return rect;
  }
}
