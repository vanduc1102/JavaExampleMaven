package my.example.design.pattern.strategy;

public class PaypalStrategy implements PaymentStrategy {

  private String emailId;
  private String password;

  public PaypalStrategy(String email, String pwd) {
    emailId = email;
    password = pwd;
  }

  @Override
  public void pay(int amount) {
    System.out.println(amount + " paid using Paypal.");
  }
}
