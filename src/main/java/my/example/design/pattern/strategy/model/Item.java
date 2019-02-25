package my.example.design.pattern.strategy.model;

public class Item {

  private String upcCode;
  private int price;

  public Item(String upc, int cost) {
    upcCode = upc;
    price = cost;
  }

  public String getUpcCode() {
    return upcCode;
  }

  int getPrice() {
    return price;
  }
}
