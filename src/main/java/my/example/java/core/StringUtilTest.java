package my.example.java.core;

import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/** @author nvduc */
public class StringUtilTest {

  public static void main(String[] args) {
    String a = "Open a browser to the following website: Microsoft Web Platform Installer 3.0";
    String b = "Open a browser to the.";
    String c = StringUtils.EMPTY;
    c += "CCCCCCCCCCCCCCCCCCCCCCC";
    System.out.println("String right pad : " + StringUtils.substring(a, 0, 50));
    System.out.println("String substr  : " + StringUtils.substring(b, 0, 50));
    System.out.println("ccccccccc : " + c);
    System.out.println("my.example.java.core.StringUtilTest.main()" + (float) 5 / 6);
    String response = "U=hrcadmin";
    System.out.println(
        "my.example.java.core.StringUtilTest.main() :"
            + response.substring(response.indexOf("U=") + "U=".length()));
    System.out.println(
        "my.example.java.core.StringUtilTest.main() " + Arrays.toString("aaa@aaa".split("@")));
    System.out.println(
        "my.example.java.core.StringUtilTest.main() " + Arrays.toString("aaaaaa".split("@")));
  }
}
