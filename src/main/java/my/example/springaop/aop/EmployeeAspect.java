package my.example.springaop.aop;

import java.util.Arrays;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class EmployeeAspect {

  @Before("execution(public String getName())")
  public void getNameAdvice(JoinPoint joinPoint) {
    System.out.println("Executing Advice on getName(): " + Arrays.toString(joinPoint.getArgs()));
  }

  @Before("execution(public void setName(*))")
  public void setNameAdvice(JoinPoint joinPoint) {
    System.out.println("Executing Advice on setName(): " + Arrays.toString(joinPoint.getArgs()));
  }

  @After("execution(public String getName())")
  public void afterNamAdvice() {
    System.out.println("Finish Advice on getName()");
  }

  @Before("execution(* my.example.springaop.service.*.get*())")
  public void getAllAdvice() {
    System.out.println(
        String.format("Service method getter called:%d", System.currentTimeMillis()));
  }
}
