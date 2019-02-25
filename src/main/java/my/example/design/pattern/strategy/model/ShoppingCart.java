package my.example.design.pattern.strategy.model;

import java.util.ArrayList;
import java.util.List;
import my.example.design.pattern.strategy.PaymentStrategy;

public class ShoppingCart {

  // List of items
  private List<Item> items;

  public ShoppingCart() {
    items = new ArrayList<>();
  }

  public void addItem(Item item) {
    items.add(item);
  }

  public void removeItem(Item item) {
    items.remove(item);
  }

  private int calculateTotal() {
    int sum = 0;
    for (Item item : items) {
      sum += item.getPrice();
    }
    return sum;
  }

  public void pay(PaymentStrategy paymentMethod) {
    int amount = calculateTotal();
    paymentMethod.pay(amount);
  }
}
