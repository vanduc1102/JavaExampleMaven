package my.example.sourcemaking.designpatterns.observer;

public class HexObserver extends Observer {
  public HexObserver(Subject subject) {
    this.subject = subject;
    this.subject.add(this);
  }

  @Override
  public void update() {
    System.out.println("" + Integer.toHexString(this.subject.getState()));
  }
}
