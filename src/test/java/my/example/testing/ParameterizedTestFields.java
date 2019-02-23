package my.example.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ParameterizedTestFields {

  // fields used together with @Parameter must be public
  @Parameter(0)
  public int m1;

  @Parameter(1)
  public int m2;

  @Parameter(2)
  public int result;

  // creates the test data
  @Parameters
  public static Collection<Object[]> data() {
    Object[][] data = new Object[][] {{1, 2, 2}, {5, 3, 15}, {121, 4, 484}};
    System.out.println("Data: " + Arrays.toString(data[0]));
    return Arrays.asList(data);
  }

  @Test
  public void testMultiplyException() {
    MyClass tester = new MyClass();
    assertEquals("Result", result, tester.multiply(m1, m2));
  }

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void testUsingTempFolder() throws IOException {
    System.currentTimeMillis();
    File createdFolder = folder.newFolder("newfolder");
    File createdFile = folder.newFile("myfilefile.txt");
    System.out.println("createdFolder: " + createdFolder.getAbsolutePath());
    System.out.println("createdFile: " + createdFile.getAbsolutePath());
    assertTrue(createdFile.exists());
  }
}
