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

public class ImageInRectange {

    public static void main(String[] args) throws IOException {
        createImageInRectagle(512, 1024, new File("./src/main/resources/chelsea.png"));
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
        File tempImage = Paths.get("target", "ok.png").toFile();
        ImageIO.write(combined, "png", tempImage);
        return tempImage;
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
