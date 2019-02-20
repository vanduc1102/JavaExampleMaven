package my.example.sourcemaking.designpatterns.observer;

import java.util.ArrayList;
import java.util.List;

public class Subject {
  private List<Observer> observers = new ArrayList<>();
  private int state;

  public void add(Observer observer) {
    this.observers.add(observer);
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  private void execute() {
    for (Observer observe : this.observers) {
      observe.update();
    }
  }
}
