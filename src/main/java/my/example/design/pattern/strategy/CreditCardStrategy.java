package my.example.design.pattern.strategy;

public class CreditCardStrategy implements PaymentStrategy {

  private final String name;
  private final String cardNumber;
  private final String cvv;

  private final String dateOfExpiry;

  public CreditCardStrategy(String nm, String ccNum, String cvv, String expiryDate) {
    name = nm;
    cardNumber = ccNum;
    this.cvv = cvv;
    dateOfExpiry = expiryDate;
  }

  @Override
  public void pay(int amount) {
    System.out.println(amount + " paid with credit/debit card");
  }
}
