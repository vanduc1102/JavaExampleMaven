package my.example.imagesignatory.utils;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class ImageUtilTest {
  private File image14File = null;
  private File chelseaFile = null;

  @Before
  public void setUp() throws Exception {
    image14File = Paths.get("src", "test", "resources", "anh14.jpg").toFile();
    chelseaFile = Paths.get("src", "test", "resources", "chelsea.jpg").toFile();
  }

  @Test
  public void scale() {}

  @Test
  public void toBufferedImage() {}

  @Test
  public void getImageRatio() {}

  @Test
  public void rotateImage180() {}

  @Test
  public void addBackgroundToImage() {}

  @Test
  public void createImageSignatory() {
    String bufferedImagePath =
        ImageUtil.createImageSignatory(
            image14File.getAbsolutePath(),
            chelseaFile.getAbsolutePath(),
            Paths.get("src/test/resources/t2i-background.png").toAbsolutePath().toString());
    assertThat(bufferedImagePath, notNullValue());
  }

  @Test
  public void mergeTwoImage() {
    String bufferedImagePath =
        ImageUtil.mergeTwoImage(
            image14File.getAbsolutePath(), chelseaFile.getAbsolutePath(), false);
    assertThat(bufferedImagePath, notNullValue());
  }

  @Test
  public void createImageFromText() {}
}
