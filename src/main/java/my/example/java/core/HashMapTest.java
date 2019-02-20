package my.example.java.core;

import java.util.HashMap;
import java.util.Map;

/** @author nvduc */
public class HashMapTest {
  public static void main(String args[]) {
    Map<String, String> domains = new HashMap<>();
    domains.put("Duc", "Hello");
    domains.put("DUC", "HELLO");
    System.out.print("Domains : " + domains.toString());
  }
}
