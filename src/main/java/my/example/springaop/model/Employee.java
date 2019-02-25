package my.example.springaop.model;

import my.example.springaop.Loggable;

public class Employee {

  private String name;

  public String getName() {
    return name;
  }

  @Loggable
  public void setName(String nm) {
    name = nm;
  }

  public void throwException() {
    throw new RuntimeException("Dummy Exception");
  }
}
