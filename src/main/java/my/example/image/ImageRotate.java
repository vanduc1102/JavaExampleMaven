package my.example.image;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nvduc
 */
public class ImageRotate {
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
    public static float rotateImage180(String imagePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
            AffineTransform transform = new AffineTransform();
            transform.rotate(Math.toRadians(180),(double) bufferedImage.getWidth()/2,(double) bufferedImage.getHeight()/2);
            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            bufferedImage = op.filter(bufferedImage, null);
            Path outputfile = Paths.get("target","saved"+getTimeStamp()+".png");
            ImageIO.write(bufferedImage, "png", outputfile.toFile());
        } catch (IOException ex) {
            
        }
        return 2;
    }
    
    public static void main(String[] args) {
        String image = "./src/main/resources/anh14.jpg";
        System.out.println("getImageRatio "+getImageRatio(image));
        rotateImage180(image);
    }
    
    public static String getTimeStamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("ddMyyyyhhmmss");
        return sdf.format(Calendar.getInstance().getTime());
    }
    
}
