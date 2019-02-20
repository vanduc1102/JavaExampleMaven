package my.example.image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;

public class AddBackground {

  public static void main(String[] args) throws IOException {
    String background = "./src/main/resources/t2i-background.png";
    String signatory = "./src/main/resources/lasa.png";
    BufferedImage bi =
        addBackgroundToImageKeepRatio2(
            ImageIO.read(new File(background)), ImageIO.read(new File(signatory)));
    ImageIO.write(bi, "PNG", Paths.get("target", "result2.png").toFile());
  }

  public static BufferedImage addBackgroundToImageKeepRatio2(
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

  public static BufferedImage addBackgroundToImageKeepRatio(
      Image backgroundImage, BufferedImage signatureImage) {

    int backgroundWidth = backgroundImage.getWidth(null);
    int backgroundHeight = backgroundImage.getHeight(null);

    Image originImage = signatureImage;
    int originImageWidth = originImage.getWidth(null);
    int originImageHeight = originImage.getHeight(null);

    boolean isPortraitSignatory = originImageHeight >= originImageWidth;
    boolean isPortraitBackground = backgroundHeight >= backgroundWidth;
    float scaleByBackground;
    int x = 0, y = 0;
    if (isPortraitBackground) {
      if (isPortraitSignatory) {
        scaleByBackground = (float) backgroundWidth / originImageHeight;
        originImage =
            originImage.getScaledInstance(
                (int) (originImageWidth * scaleByBackground), backgroundWidth, Image.SCALE_SMOOTH);
        x = (backgroundWidth - (int) (originImageWidth * scaleByBackground)) / 2;
        y = (backgroundHeight - backgroundWidth) / 2;
      } else {
        scaleByBackground = (float) backgroundWidth / originImageWidth;
        originImage =
            originImage.getScaledInstance(
                backgroundWidth, (int) (originImageHeight * scaleByBackground), Image.SCALE_SMOOTH);
        y = (backgroundHeight - (int) (originImageHeight * scaleByBackground)) / 2;
      }
    } else if (isPortraitSignatory) {
      scaleByBackground = (float) backgroundHeight / originImageHeight;
      originImage =
          originImage.getScaledInstance(
              (int) (originImageWidth * scaleByBackground), backgroundHeight, Image.SCALE_SMOOTH);
      x = (backgroundWidth - (int) (originImageWidth * scaleByBackground)) / 2;
    } else {
      scaleByBackground = (float) backgroundHeight / originImageWidth;
      originImage =
          originImage.getScaledInstance(
              backgroundHeight, (int) (originImageHeight * scaleByBackground), Image.SCALE_SMOOTH);
      y = (backgroundHeight - (int) (originImageHeight * scaleByBackground)) / 2;
      x = (backgroundWidth - backgroundHeight) / 2;
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

  public static BufferedImage scale(BufferedImage imgb, int newWidth, int newHeight) {
    Image image;
    image = imgb.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    return toBufferedImage(image);
  }

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
}
