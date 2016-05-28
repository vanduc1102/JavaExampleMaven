package my.example.javaexamplemaven.utils;


import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author nvduc
 */
public class ImageUtil {

    private ImageUtil() {
    }
    
    public static InputStream scale(InputStream is, int newWidth, int newHeight) {
        Image image;
        try {
            image = ImageIO.read(is);
            image = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            BufferedImage bImage = toBufferedImage(image);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bImage, "JPG", os);
            InputStream inputStream = new ByteArrayInputStream(os.toByteArray());
            return inputStream;
        } catch (IOException ex) {
            return is;
        }

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

    public static float getImageRatio(String imagePath) {
        try {
            Image iamge = ImageIO.read(new File(imagePath));
            int width = iamge.getWidth(null);
            int height = iamge.getHeight(null);
            return (float) width / height;
        } catch (IOException ex) {
        }
        return 2;
    }
    
    public static String rotateImage180(final String imagePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(180),(double) bufferedImage.getWidth()/2,(double) bufferedImage.getHeight()/2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            bufferedImage = op.filter(bufferedImage, null);
            String rotatedImagePath = IOUtils.getTemporyFilePath();
            File outputfile = new File(rotatedImagePath);
            ImageIO.write(bufferedImage, "png", outputfile);
            return rotatedImagePath;
        } catch (IOException ex) {
        }
        return StringUtils.EMPTY;
    }

    public static String addBackgroundToImage(String backgroundPATH, String imagePath) {
        try {
            String tempFileName = IOUtils.getTemporyFilePath();
            Image backgroundImage = ImageIO.read(new File(backgroundPATH));
            int backgroundWidth = backgroundImage.getWidth(null);
            int backgroundHeight = backgroundImage.getHeight(null);

            Image signAndText = ImageIO.read(new File(imagePath));
            
            signAndText = signAndText.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);
            signAndText = toBufferedImage(signAndText);

            BufferedImage combined = new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g2 = combined.getGraphics();
            g2.drawImage(backgroundImage, 0, 0, null);
            g2.drawImage(signAndText, 0, 0, null);
            g2.dispose();
            ImageIO.write(combined, "JPG", new File(tempFileName));
            return tempFileName;
        } catch (IOException ex) {
            return null;
        }
    }
    
    public static String createImageSignatory(String backgroundPath, String signatoryPath, String textImagePath) {
        try {
            String tempFileName = IOUtils.getTemporyFilePath();
            Image backgroundImage = ImageIO.read(new File(backgroundPath));
            int backgroundWidth = backgroundImage.getWidth(null);
            int backgroundHeight = backgroundImage.getHeight(null);
            
            Image textImage = ImageIO.read(new File(textImagePath));
            int textImageWidth = textImage.getWidth(null);
            int textImageHeight = textImage.getHeight(null);
            
            float ratioTextOnBackground = (float) backgroundWidth / textImageWidth;
            textImageWidth = (int)(textImageWidth * ratioTextOnBackground);
            textImageHeight = (int)(textImageHeight * ratioTextOnBackground);
            textImage = textImage.getScaledInstance(textImageWidth, textImageHeight, Image.SCALE_SMOOTH);
            textImage = toBufferedImage(textImage);
            
            Image signatoryImage = ImageIO.read(new File(signatoryPath));
            int widthSign = signatoryImage.getWidth(null);
            int heightSign = signatoryImage.getHeight(null);
            
            int backgroundRemainingHeight = backgroundHeight - textImageHeight;
            if(backgroundRemainingHeight <=0){
                System.out.println("The image text is too height,We dont have place for Signatory image. Reducing the lenght of the signatory reason may help.");
            }
            float ratioWidth = (float) widthSign / backgroundWidth;
            float ratioHeight = (float) heightSign / backgroundRemainingHeight;
            float ratioSignatoryOnRemaningSpace = ratioWidth > ratioHeight ? ratioWidth : ratioHeight;
            widthSign = (int)(widthSign / ratioSignatoryOnRemaningSpace);
            heightSign = (int)(heightSign / ratioSignatoryOnRemaningSpace);
            signatoryImage = signatoryImage.getScaledInstance(widthSign, heightSign, Image.SCALE_SMOOTH);
            signatoryImage = toBufferedImage(signatoryImage);

            BufferedImage combined = new BufferedImage(backgroundWidth, backgroundHeight, BufferedImage.TYPE_INT_RGB);
            Graphics g2 = combined.getGraphics();
            g2.drawImage(backgroundImage, 0, 0, null);
            if(backgroundWidth > widthSign){
                int tempX = (backgroundWidth - widthSign) / 2;
                g2.drawImage(signatoryImage, tempX, 0, null);
            }else if( backgroundRemainingHeight > heightSign){
                int tempY =  (backgroundRemainingHeight - heightSign) / 2;
                g2.drawImage(signatoryImage, 0, tempY, null);
            }else{
                g2.drawImage(signatoryImage, 0, 0, null);
            }           
            g2.drawImage(textImage, 0, backgroundHeight - textImageHeight, null);
            g2.dispose();
            ImageIO.write(combined, "JPG", new File(tempFileName));
            return tempFileName;
        } catch (IOException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public static String mergeTwoImage(String first, String second, boolean isHasBackground) {
        try {
            String tempFileName = IOUtils.getTemporyFilePath();
            Image signatoryImage = ImageIO.read(new File(first));
            Image textImage = ImageIO.read(new File(second));
            float ratio = (float) signatoryImage.getWidth(null) / (float) textImage.getWidth(null);
            int newHeight = (int) (ratio * (float) textImage.getHeight(null));
            int newWidth = (int) (ratio * (float) textImage.getWidth(null));
            textImage = textImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            textImage = toBufferedImage(textImage);
            BufferedImage combinedTemp;
            if (isHasBackground) {
                combinedTemp = new BufferedImage(signatoryImage.getWidth(null), signatoryImage.getHeight(null) + newHeight, BufferedImage.TYPE_INT_ARGB);
            } else {
                combinedTemp = new BufferedImage(signatoryImage.getWidth(null), signatoryImage.getHeight(null) + newHeight, BufferedImage.TYPE_INT_RGB);
            }

            Graphics g = combinedTemp.getGraphics();
            g.drawImage(signatoryImage, 0, 0, null);
            g.drawImage(textImage, 0, signatoryImage.getHeight(null), null);
            g.dispose();
            if (isHasBackground) {
                ImageIO.write(combinedTemp, "PNG", new File(tempFileName));
            } else {
                ImageIO.write(combinedTemp, "JPG", new File(tempFileName));
            }
            return tempFileName;
        } catch (IOException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public static String createImageFromText(String text, boolean isNeededBackground,final int fontSize) {
        int tempFontSize = fontSize;
        if(tempFontSize == 0){
            tempFontSize = 13;
        }
        String tempFileName = IOUtils.getTemporyFilePath();
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
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
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
        try {
            if (isNeededBackground) {
                ImageIO.write(img, "PNG", new File(tempFileName));
            } else {
                ImageIO.write(img, "JPG", new File(tempFileName));
            }
        } catch (IOException ex) {
            System.out.println(ex);
            return null;
        }
        return tempFileName;
    }

}
