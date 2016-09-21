package my.example.image;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class AddBackground {

    public static void main(String[] args) throws IOException {
        String background = "./src/main/resources/sign.jpg";
        String signatory = "./src/main/resources/chelsea.jpg";
        addBackgroundToImageKeepRatio(background, signatory);
    }

    public static String addBackgroundToImageKeepRatio(String backgroundPATH, String imagePath) {
        System.out.println("my.example.image.AddBackground.addBackgroundToImageKeepRatio()");
        try {
            File resultImagePath = Paths.get("target", "temp.png").toFile();
            Image backgroundImage = ImageIO.read(new File(backgroundPATH));
            int backgroundWidth = backgroundImage.getWidth(null);
            int backgroundHeight = backgroundImage.getHeight(null);

            Image originImage = ImageIO.read(new File(imagePath));
            int originImageWidth = originImage.getWidth(null);
            int originImageHeight = originImage.getHeight(null);

            boolean isPortrait = originImageHeight >= originImageWidth;
            boolean isPortraitBackground = backgroundHeight >= backgroundWidth;
            float scaleByBackground;
            if (isPortraitBackground) {
                if (isPortrait) {
                    scaleByBackground = (float) backgroundWidth / originImageHeight;
                    originImage = originImage.getScaledInstance((int) (originImageWidth * scaleByBackground), backgroundWidth, Image.SCALE_SMOOTH);
                } else {
                    scaleByBackground = (float) backgroundWidth / originImageWidth;
                    originImage = originImage.getScaledInstance(backgroundWidth, (int) (originImageHeight * scaleByBackground), Image.SCALE_SMOOTH);
                }
            } else if (isPortrait) {
                scaleByBackground = (float) backgroundHeight / originImageHeight;
                originImage = originImage.getScaledInstance((int) (originImageWidth * scaleByBackground), backgroundHeight, Image.SCALE_SMOOTH);
            } else {
                scaleByBackground = (float) backgroundHeight / originImageWidth;
                originImage = originImage.getScaledInstance(backgroundHeight, (int) (originImageHeight * scaleByBackground), Image.SCALE_SMOOTH);
            }

            originImage = toBufferedImage(originImage);

            BufferedImage combined = new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g2 = combined.getGraphics();
            g2.drawImage(backgroundImage, 0, 0, null);
            if (isPortraitBackground) {
                if (isPortrait) {
                    int x = (backgroundWidth - (int) (originImageWidth * scaleByBackground)) / 2;
                    int y = (backgroundHeight - backgroundWidth) / 2;
                    g2.drawImage(originImage, x, y, null);
                } else {
                    int y = (backgroundHeight - (int) (originImageHeight * scaleByBackground)) / 2;
                    g2.drawImage(originImage, 0, y, null);
                }
            } else if (isPortrait) {
                int x = (backgroundWidth - (int) (originImageWidth * scaleByBackground)) / 2;
                g2.drawImage(originImage, x, 0, null);
            } else {
                int y = (backgroundHeight - (int) (originImageHeight * scaleByBackground)) / 2;
                int x = (backgroundWidth - backgroundHeight) / 2;
                g2.drawImage(originImage, x, y, null);
            }
            g2.dispose();
            ImageIO.write(combined, "JPG", resultImagePath);
            return resultImagePath.getAbsolutePath();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
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
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}
