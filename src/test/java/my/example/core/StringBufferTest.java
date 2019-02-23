package my.example.core;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/** @author nvduc */
public class StringBufferTest {

  public StringBufferTest() {}

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
  public void sbTest() {
    String hello = "Hello";
    StringBuffer sb = new StringBuffer(hello);
    assertTrue(hello.equals(sb.toString()));
  }

  @Test
  public void bufferToBuilder() {
    String hello = "Hello";
    StringBuffer sb = new StringBuffer(hello);
    StringBuilder stringBuilder = new StringBuilder(sb);
    assertTrue(hello.equals(stringBuilder.toString()));
  }
}
