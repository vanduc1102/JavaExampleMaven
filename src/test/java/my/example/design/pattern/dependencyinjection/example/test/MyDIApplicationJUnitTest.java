package my.example.design.pattern.dependencyinjection.example.test;

import my.example.design.pattern.dependencyinjection.example.MessageService;
import my.example.design.pattern.dependencyinjection.example.consumer.Consumer;
import my.example.design.pattern.dependencyinjection.example.consumer.MyDIApplication;
import my.example.design.pattern.dependencyinjection.example.injector.MessageServiceInjector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MyDIApplicationJUnitTest {

  private MessageServiceInjector injector;

  @Before
  public void setUp() {
    // mock the injector with anonymous class
    injector =
        new MessageServiceInjector() {

          @Override
          public Consumer getConsumer() {
            // mock the message service
            return new MyDIApplication(
                new MessageService() {

                  @Override
                  public void sendMessage(String msg, String rec) {
                    System.out.println("Mock Message Service implementation");
                  }
                });
          }
        };
  }

  @Test
  public void test() {
    Consumer consumer = injector.getConsumer();
    consumer.processMessages("Hi Pankaj", "pankaj@abc.com");
  }

  @After
  public void tear() {
    injector = null;
  }
}
