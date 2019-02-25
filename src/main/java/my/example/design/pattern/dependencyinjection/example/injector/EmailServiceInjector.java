package my.example.design.pattern.dependencyinjection.example.injector;

import my.example.design.pattern.dependencyinjection.example.EmailServiceImpl;
import my.example.design.pattern.dependencyinjection.example.consumer.Consumer;
import my.example.design.pattern.dependencyinjection.example.consumer.MyDIApplication;

public class EmailServiceInjector implements MessageServiceInjector {

  @Override
  public Consumer getConsumer() {
    return new MyDIApplication(new EmailServiceImpl());
  }
}
