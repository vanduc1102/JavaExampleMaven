package my.example.springaop.service;

import my.example.springaop.model.Employee;

public class EmployeeService {

  private Employee employee;

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee e) {
    employee = e;
  }
}
