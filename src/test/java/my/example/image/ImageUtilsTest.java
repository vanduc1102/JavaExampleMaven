package my.example.image;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImageUtilsTest {
  private File image14File = null;

  @Before
  public void setUp() throws Exception {
    image14File = Paths.get("src", "test", "resources", "anh14.jpg").toFile();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void toBufferedImage_isBuffered() {
    try {
      Image image = ImageIO.read(image14File);
      BufferedImage bufferImage = ImageUtils.toBufferedImage(image);
      assertThat(bufferImage, instanceOf(BufferedImage.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void getImageRatio() {
    File imageFile = image14File;
    float imageRatio = Math.round(ImageUtils.getImageRatio(imageFile.getAbsolutePath()));
    assertThat(imageRatio, is((float) Math.round(2.0 / 3.0)));
  }

  @Test
  public void getImageRatio_ReturnDefault() {
    File imageFile = image14File;
    float imageRatio =
        Math.round(ImageUtils.getImageRatio(imageFile.getAbsolutePath().concat("abc")));
    assertThat(imageRatio, is((float) 2.0));
  }

  @Test
  public void rotateImage() {}

  @Test
  public void rotateImage180() {}

  @Test
  public void transform() {
    try {
      BufferedImage rotated = ImageUtils.transform(ImageIO.read(image14File), 1);
      assertThat(rotated, instanceOf(BufferedImage.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void transform_quadrants_3() {
    try {
      BufferedImage rotated = ImageUtils.transform(ImageIO.read(image14File), 3);
      assertThat(rotated, instanceOf(BufferedImage.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void addBackgroundToImage() {
    BufferedImage textBufferedImage =
        ImageUtils.createImageFromText("text - duc nguyen", false, 14);
    try {
      BufferedImage bufferedImage =
          ImageUtils.mergeTwoImage(ImageIO.read(image14File), textBufferedImage, true);
      assertThat(bufferedImage, instanceOf(BufferedImage.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void addBackgroundToImageKeepRatio() {
    BufferedImage textBufferedImage =
        ImageUtils.createImageFromText("text - duc nguyen", false, 14);
    try {
      BufferedImage bufferedImage =
          ImageUtils.addBackgroundToImageKeepRatio(ImageIO.read(image14File), textBufferedImage);
      assertThat(bufferedImage, instanceOf(BufferedImage.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void createImageSignatory() {
    BufferedImage textBufferedImage =
        ImageUtils.createImageFromText("text - duc nguyen", false, 14);
    BufferedImage signatoryBufferedImage =
        ImageUtils.createImageFromText("sign - duc nguyen", false, 14);
    try {
      Image createdImage =
          ImageUtils.createImageSignatory(
              ImageIO.read(image14File), textBufferedImage, signatoryBufferedImage);
      assertThat(createdImage, instanceOf(BufferedImage.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void mergeTwoImage() {}

  @Test
  public void createImageFromText() {
    BufferedImage bufferedImage = ImageUtils.createImageFromText("duc nguyen", false, 14);
    assertThat(bufferedImage, instanceOf(BufferedImage.class));
  }

  @Test
  public void createImageFromText_withBG() {
    BufferedImage bufferedImage = ImageUtils.createImageFromText("duc nguyen", true, 14);
    assertThat(bufferedImage, instanceOf(BufferedImage.class));
  }

  @Test
  public void convertPixel2Point() {}

  @Test
  public void createImageInRectagle() {
    File outputImg = null;
    try {
      outputImg = ImageUtils.createImageInRectagle(200, 100, image14File);
      assertThat(outputImg.getAbsolutePath(), notNullValue());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void scale() {}

  @Test
  public void rotateImage1() {
    String outputImgPath = null;
    try {
      outputImgPath = ImageUtils.rotateImage180(image14File.getAbsolutePath());
      assertThat(outputImgPath, notNullValue());
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        Files.delete(Paths.get(outputImgPath));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Test
  public void rotateImageBuffered() {
    try {
      File outputImg = ImageUtils.rotateImage(ImageIO.read(image14File), 180.0);
      assertThat(outputImg, instanceOf(File.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
