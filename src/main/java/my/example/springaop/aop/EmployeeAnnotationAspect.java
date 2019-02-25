package my.example.springaop.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class EmployeeAnnotationAspect {

  @Before("@annotation(my.example.springaop.Loggable)")
  public void myAdvice() {
    System.out.println("Executing myAdvice!!");
  }
}
