package my.example.http.client;

/** @author nvduc */
public class HttpClientSSH {
  public static void main(String[] args) throws InterruptedException {
    int i = 10;
    while (i == 10) {
      i++;
      Thread.sleep(5000l);
      System.out.println(
          "my.example.http.client.HttpClientSSH.main() " + System.currentTimeMillis());
    }
  }
}
