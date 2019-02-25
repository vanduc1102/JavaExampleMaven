package my.example.springaop;

import my.example.springaop.service.EmployeeService;
import org.apache.log4j.BasicConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

  public static void main(String[] args) {
    BasicConfigurator.configure();
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
    System.out.println(String.format("=========>%d Start Spring DI", System.currentTimeMillis()));
    EmployeeService employeeService = ctx.getBean("employeeService", EmployeeService.class);

    System.out.println(employeeService.getEmployee().getName());

    employeeService.getEmployee().setName("Pankaj");
    try {
      employeeService.getEmployee().throwException();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.out.println(String.format("=========>%d End Spring DI", System.currentTimeMillis()));
    ctx.close();
  }
}
