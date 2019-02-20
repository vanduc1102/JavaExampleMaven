package my.example.java.core;

import java.util.HashSet;
import java.util.Set;

/** @author nvduc */
public class SetExample {
  public static void main(String[] args) {
    Set<String> setName = new HashSet<>();
    setName.add("AAAAAAA");
    setName.add("BBBBBBB");
    setName.add("CCCCCCC");
    setName.add("GGGGGGG");
    System.out.println("my.example.java.core.SetExample.main() : " + setName.toString());
  }
}
