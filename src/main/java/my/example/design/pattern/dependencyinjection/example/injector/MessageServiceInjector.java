package my.example.design.pattern.dependencyinjection.example.injector;

import my.example.design.pattern.dependencyinjection.example.consumer.Consumer;

public interface MessageServiceInjector {

  public Consumer getConsumer();
}
