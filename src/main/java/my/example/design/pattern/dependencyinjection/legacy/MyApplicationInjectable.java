package my.example.design.pattern.dependencyinjection.legacy;

public class MyApplicationInjectable {

  private EmailService email = null;

  public MyApplicationInjectable(EmailService email) {
    this.email = email;
  }

  public void processMessages(String msg, String rec) {
    // do some msg validation, manipulation logic etc
    this.email.sendEmail(msg, rec);
  }
}
