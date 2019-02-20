package my.example.file.system;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** @author nvduc */
public class WriteHugeString {
  public static void main(String[] args) throws IOException {
    String hugeString =
        "https://www.google.com.vn/search?q=ejb+get+data+from+future+object&oq=ejb+get+data+from+future+object&aqs=chrome..69i57.9047j0j4&sourceid=chrome&ie=UTF-8#q=ejb+get+data+from+future+object+example";
    Path temp = Files.createTempFile("aaaaaa", "hugeString");
    Files.write(temp, hugeString.getBytes());
    System.out.println("Path : " + temp.toFile().getAbsolutePath());
  }
}
