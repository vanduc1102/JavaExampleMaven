package my.example.core;

import static org.junit.Assert.assertTrue;

import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** @author nvduc */
public class ObjectsTest {

  public ObjectsTest() {}

  @BeforeClass
  public static void setUpClass() {}

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  // TODO add test methods here.
  // The methods must be annotated with annotation @Test. For example:
  //
  @Test
  public void testEquals() {
    assertTrue(" TRUE null : FALSE", !Objects.equals(Boolean.TRUE, null));
    assertTrue(" null null : TRUE", Objects.equals(null, null));
    assertTrue(" null TRUE : FALSE", !Objects.equals(null, Boolean.TRUE));
  }
}
