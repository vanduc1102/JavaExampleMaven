package my.example.java.core;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author nvduc
 */
public class StringUtilTest {

    public static void main(String[] args) {
        String a = "Open a browser to the following website: Microsoft Web Platform Installer 3.0";
        String b = "Open a browser to the.";
        System.out.println("String right pad : " + StringUtils.substring(a, 0,50));
        System.out.println("String substr  : " + StringUtils.substring(b, 0,50));
    }
}