package my.example.design.pattern.proxy;

public class ProxyPatternDemo {
  public static void main(String[] args) {
    Image image = new ProxyImage("test_10mb.png");
    image.display();
    System.out.println("Next time display:");
    image.display();
  }
}
