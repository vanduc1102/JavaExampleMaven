package my.example.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import my.example.imagesignatory.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

/** @author nvduc */
public class ImageUtils {

  private static final org.slf4j.Logger log = LoggerFactory.getLogger(ImageUtils.class);

  private ImageUtils() {}

  public static BufferedImage toBufferedImage(Image img) {
    if (img instanceof BufferedImage) {
      return (BufferedImage) img;
    }

    BufferedImage bimage =
        new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    Graphics2D bGr = bimage.createGraphics();
    bGr.drawImage(img, 0, 0, null);
    bGr.dispose();

    return bimage;
  }

  public static float getImageRatio(String imagePath) {
    try {
      Image iamge = ImageIO.read(new File(imagePath));
      int width = iamge.getWidth(null);
      int height = iamge.getHeight(null);
      return (float) width / height;
    } catch (IOException ex) {
      log.error("getImageRatio ", ex);
    }
    return 2;
  }

  public static File rotateImage(final File imagePath, int numquadrants) {
    try {
      BufferedImage bufferedImage = ImageIO.read(imagePath);
      bufferedImage = transform(bufferedImage, numquadrants);
      File outputfile = IOUtils.getTemporyFile("rotated" + numquadrants, ".png");
      ImageIO.write(bufferedImage, "png", outputfile);
      return outputfile;
    } catch (IOException ex) {
      log.debug("rotateImage", ex);
    }
    return null;
  }

  public static String rotateImage180(final String imagePath) {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
      AffineTransform transform = new AffineTransform();
      transform.rotate(
          Math.toRadians(180),
          (double) bufferedImage.getWidth() / 2,
          (double) bufferedImage.getHeight() / 2);
      AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
      bufferedImage = op.filter(bufferedImage, null);
      String rotatedImagePath = IOUtils.getTemporyFilePath();
      File outputfile = new File(rotatedImagePath);
      ImageIO.write(bufferedImage, "png", outputfile);
      return rotatedImagePath;
    } catch (IOException ex) {
      log.debug("rotateImage180 - Can not rotate image 180 degree :", ex);
    }
    return StringUtils.EMPTY;
  }

  /**
   * Transform image by numquadrants This method from example :
   * http://stackoverflow.com/questions/8719473/affinetransform-truncates-image-what-do-i-wrong
   *
   * @param image
   * @param numquadrants
   * @return
   */
  public static BufferedImage transform(BufferedImage image, int numquadrants) {
    int w0 = image.getWidth();
    int h0 = image.getHeight();
    int w1 = w0;
    int h1 = h0;

    int centerX = w0 / 2;
    int centerY = h0 / 2;

    if (numquadrants % 2 == 1) {
      w1 = h0;
      h1 = w0;
    }

    if (numquadrants % 4 == 1) {
      if (w0 > h0) {
        centerX = h0 / 2;
        centerY = h0 / 2;
      } else if (h0 > w0) {
        centerX = w0 / 2;
        centerY = w0 / 2;
      }
      // if h0 == w0, then use default
    } else if (numquadrants % 4 == 3) {
      if (w0 > h0) {
        centerX = w0 / 2;
        centerY = w0 / 2;
      } else if (h0 > w0) {
        centerX = h0 / 2;
        centerY = h0 / 2;
      }
      // if h0 == w0, then use default
    }

    AffineTransform affineTransform = new AffineTransform();
    affineTransform.setToQuadrantRotation(numquadrants, centerX, centerY);

    AffineTransformOp opRotated =
        new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_BILINEAR);

    BufferedImage transformedImage = new BufferedImage(w1, h1, image.getType());

    transformedImage = opRotated.filter(image, transformedImage);
    return transformedImage;
  }

  public static BufferedImage addBackgroundToImage(
      Image backgroundImage, BufferedImage signAndTextBufferedImage) {
    int backgroundWidth = backgroundImage.getWidth(null);
    int backgroundHeight = backgroundImage.getHeight(null);

    Image signAndText =
        signAndTextBufferedImage.getScaledInstance(
            backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);
    signAndText = toBufferedImage(signAndText);

    BufferedImage combined =
        new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_RGB);
    Graphics g2 = combined.getGraphics();
    g2.drawImage(backgroundImage, 0, 0, null);
    g2.drawImage(signAndText, 0, 0, null);
    g2.dispose();
    return combined; // jpg file
  }

  public static BufferedImage addBackgroundToImageKeepRatio(
      Image backgroundImage, BufferedImage signatureImage) {
    int backgroundWidth = backgroundImage.getWidth(null);
    int backgroundHeight = backgroundImage.getHeight(null);

    Image originImage = signatureImage;
    int originImageWidth = originImage.getWidth(null);
    int originImageHeight = originImage.getHeight(null);

    float hRatio = (float) backgroundHeight / originImageHeight;
    float wRatio = (float) backgroundWidth / originImageWidth;
    int newOriginImageHeight;
    int newOriginImageWidth = (int) (hRatio * originImageWidth);
    int x = 0, y = 0;
    if (newOriginImageWidth > backgroundWidth) {
      newOriginImageHeight = (int) (backgroundHeight * wRatio);
      newOriginImageWidth = backgroundWidth;
      originImage =
          originImage.getScaledInstance(
              newOriginImageWidth, newOriginImageHeight, Image.SCALE_SMOOTH);
      y = (backgroundHeight - newOriginImageHeight) / 2;
    } else {
      newOriginImageHeight = backgroundHeight;
      originImage =
          originImage.getScaledInstance(
              newOriginImageWidth, newOriginImageHeight, Image.SCALE_SMOOTH);
      x = (backgroundWidth - newOriginImageWidth) / 2;
    }
    originImage = toBufferedImage(originImage);
    BufferedImage combined =
        new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_RGB);
    Graphics g2 = combined.getGraphics();
    g2.drawImage(backgroundImage, 0, 0, null);
    g2.drawImage(originImage, x, y, null);
    g2.dispose();
    return combined;
  }

  public static BufferedImage createImageSignatory(
      Image backgroundImage,
      BufferedImage signatoryImageBuffered,
      BufferedImage textImageBuffered) {

    int backgroundWidth = backgroundImage.getWidth(null);
    int backgroundHeight = backgroundImage.getHeight(null);

    Image textImage = textImageBuffered;
    int textImageWidth = textImage.getWidth(null);
    int textImageHeight = textImage.getHeight(null);

    float ratioTextOnBackground = (float) backgroundWidth / textImageWidth;
    textImageWidth = (int) (textImageWidth * ratioTextOnBackground);
    textImageHeight = (int) (textImageHeight * ratioTextOnBackground);
    textImage = textImage.getScaledInstance(textImageWidth, textImageHeight, Image.SCALE_SMOOTH);
    textImage = toBufferedImage(textImage);

    Image signatoryImage = signatoryImageBuffered;
    int widthSign = signatoryImage.getWidth(null);
    int heightSign = signatoryImage.getHeight(null);

    int backgroundRemainingHeight = backgroundHeight - textImageHeight;
    if (backgroundRemainingHeight <= 0) {
      log.info(
          "The image text is too height,We dont have place for Signatory image. Reducing the lenght of the signatory reason may help.");
    }
    float ratioWidth = (float) widthSign / backgroundWidth;
    float ratioHeight = (float) heightSign / backgroundRemainingHeight;
    float ratioSignatoryOnRemaningSpace = ratioWidth > ratioHeight ? ratioWidth : ratioHeight;
    widthSign = (int) (widthSign / ratioSignatoryOnRemaningSpace);
    heightSign = (int) (heightSign / ratioSignatoryOnRemaningSpace);
    signatoryImage = signatoryImage.getScaledInstance(widthSign, heightSign, Image.SCALE_SMOOTH);
    signatoryImage = toBufferedImage(signatoryImage);

    BufferedImage combined =
        new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_RGB);
    Graphics g2 = combined.getGraphics();
    g2.drawImage(backgroundImage, 0, 0, null);
    if (backgroundWidth > widthSign) {
      int tempX = (backgroundWidth - widthSign) / 2;
      g2.drawImage(signatoryImage, tempX, 0, null);
    } else if (backgroundRemainingHeight > heightSign) {
      int tempY = (backgroundRemainingHeight - heightSign) / 2;
      g2.drawImage(signatoryImage, 0, tempY, null);
    } else {
      g2.drawImage(signatoryImage, 0, 0, null);
    }
    g2.drawImage(textImage, 0, backgroundHeight - textImageHeight, null);
    g2.dispose();

    return combined; // JPG
  }

  public static BufferedImage mergeTwoImage(
      BufferedImage first, BufferedImage second, boolean isHasBackground) {
    Image signatoryImage = first;
    Image textImage = second;
    float ratio = (float) signatoryImage.getWidth(null) / (float) textImage.getWidth(null);
    int newHeight = (int) (ratio * (float) textImage.getHeight(null));
    int newWidth = (int) (ratio * (float) textImage.getWidth(null));
    textImage = textImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    textImage = toBufferedImage(textImage);
    BufferedImage combinedTemp;
    if (isHasBackground) {
      combinedTemp =
          new BufferedImage(
              signatoryImage.getWidth(null),
              signatoryImage.getHeight(null) + newHeight,
              BufferedImage.TYPE_INT_ARGB);
    } else {
      combinedTemp =
          new BufferedImage(
              signatoryImage.getWidth(null),
              signatoryImage.getHeight(null) + newHeight,
              BufferedImage.TYPE_INT_RGB);
    }

    Graphics g = combinedTemp.getGraphics();

    if (isHasBackground) {
      g.drawImage(signatoryImage, 0, 0, null);
      g.drawImage(textImage, 0, signatoryImage.getHeight(null), null);
      g.dispose();
      // ImageIO.write(combinedTemp, "PNG", new File(resultImagePath));
    } else {
      g.drawImage(signatoryImage, 0, 0, Color.WHITE, null);
      g.drawImage(textImage, 0, signatoryImage.getHeight(null), Color.WHITE, null);
      g.dispose();
      // ImageIO.write(combinedTemp, "JPG", new File(resultImagePath));
    }

    return combinedTemp;
  }

  public static BufferedImage createImageFromText(
      String text, boolean isNeededBackground, final int fontSize) {
    int tempFontSize = fontSize;
    if (tempFontSize == 0) {
      tempFontSize = 13;
    }
    // String resultImagePath;
    String[] textParts = text.split("\n");

    BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = img.createGraphics();
    Font font = new Font("Arial", Font.PLAIN, tempFontSize);
    g2d.setFont(font);
    FontMetrics fm = g2d.getFontMetrics();
    int width = 0;
    for (String textPart : textParts) {
      int tempWidth = fm.stringWidth(textPart);
      if (tempWidth > width) {
        width = tempWidth;
      }
    }
    width += 10;
    int oneLineHeight = fm.getHeight();
    int height = (oneLineHeight) * textParts.length;
    g2d.dispose();
    if (isNeededBackground) {
      img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    } else {
      img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }
    g2d = img.createGraphics();
    g2d.setRenderingHint(
        RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(
        RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(
        RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    g2d.setFont(font);
    fm = g2d.getFontMetrics();
    if (!isNeededBackground) {
      g2d.setPaint(new Color(255, 255, 255));
      g2d.fillRect(0, 0, width, height);
      g2d.setBackground(Color.WHITE);
    }
    g2d.setColor(Color.BLACK);
    int index = 0;
    for (String textPart : textParts) {
      g2d.drawString(textPart, 5, (oneLineHeight) * index + fm.getAscent());
      index++;
    }

    g2d.dispose();
    return img;
  }

  public static float convertPixel2Point(float pixel) {
    return pixel * (float) 72 / 96;
  }

  public static File createImageInRectagle(int width, int height, File image) throws IOException {
    Image originImage = ImageIO.read(image);
    int oImgWidth = originImage.getWidth(null);
    int oImgHeight = originImage.getHeight(null);
    float imageRatio = (float) oImgHeight / oImgWidth;
    float rectangleRatio = (float) width / height;

    BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    BufferedImage bufferedImage = ImageIO.read(image);
    Graphics g2 = combined.getGraphics();
    Point p = new Point(0, 0);
    if (Math.abs(imageRatio - rectangleRatio) < 0.05) {
      bufferedImage = scale(bufferedImage, width, height);
      p.x = 0;
      p.y = 0;
    } else {
      int minEdge = width < height ? width : height;
      if (oImgHeight > oImgWidth) {
        float diff = (float) oImgHeight / minEdge;
        int newWidth = Math.round(oImgWidth / diff);
        bufferedImage = scale(bufferedImage, newWidth, minEdge);
        if (minEdge == height) {
          p.x = Math.abs((width - newWidth) / 2);
          p.y = 0;
        } else {
          p.x = 0;
          p.y = Math.abs((height - minEdge) / 2);
        }
      } else {
        float diff = (float) oImgWidth / minEdge;
        int newHeight = Math.round(oImgHeight / diff);
        bufferedImage = scale(bufferedImage, minEdge, newHeight);
        if (minEdge == height) {
          p.x = Math.abs((width - minEdge) / 2);
          p.y = 0;
        } else {
          p.x = 0;
          p.y = Math.abs((height - newHeight) / 2);
        }
      }
    }
    g2.drawImage(bufferedImage, p.x, p.y, null);
    g2.dispose();
    File tempImage = new File(IOUtils.getTemporyFilePath("image", ".png"));
    ImageIO.write(combined, "png", tempImage);
    return tempImage;
  }

  public static BufferedImage scale(BufferedImage imgb, int newWidth, int newHeight) {
    Image image;
    image = imgb.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    return toBufferedImage(image);
  }

  public static File rotateImage(BufferedImage image, double angle) throws IOException {
    BufferedImage rotated = rotateImageBuffered(image, angle);
    String imagePath = IOUtils.getTemporyFilePath(".png");
    File imageFile = new File(imagePath);
    ImageIO.write(rotated, "png", imageFile);
    return imageFile;
  }

  public static BufferedImage rotateImageBuffered(BufferedImage image, double angle) {
    double rads = Math.toRadians(angle);
    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
    int w = image.getWidth();
    int h = image.getHeight();
    int newWidth = (int) Math.floor(w * cos + h * sin);
    int newHeight = (int) Math.floor(h * cos + w * sin);

    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = rotated.createGraphics();
    AffineTransform at = new AffineTransform();
    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

    int x = w / 2;
    int y = h / 2;

    at.rotate(Math.toRadians(angle), x, y);
    g2d.setTransform(at);
    g2d.drawImage(image, 0, 0, null);
    g2d.dispose();
    return rotated;
  }
}
