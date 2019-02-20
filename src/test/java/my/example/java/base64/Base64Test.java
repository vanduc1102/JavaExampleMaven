package my.example.java.base64;

import java.util.Base64;
import org.junit.Assert;
import org.junit.Test;

/** @author nvduc */
public class Base64Test {

  public Base64Test() {}

  @Test
  public void test() {
    String origin = "Hello World";
    String base64 = new String(Base64.getEncoder().encode(origin.getBytes()));
    String fromBase64 = new String(Base64.getDecoder().decode(base64));
    System.out.println("text Encoded : " + base64);
    System.out.println("text Decoded : " + fromBase64);
    Assert.assertEquals(origin, fromBase64);
  }
}
