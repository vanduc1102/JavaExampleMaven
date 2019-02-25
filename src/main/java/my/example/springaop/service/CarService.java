package my.example.springaop.service;

import my.example.core.UpdateList.Employee;

public class CarService {
  private String name;

  private Employee owner;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Employee getOwner() {
    return owner;
  }

  public void setOwner(Employee owner) {
    this.owner = owner;
  }
}
