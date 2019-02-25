package my.example.spring.consumer;

import my.example.spring.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MyApplication {

  // field-based dependency injection @Autowired
  //  @Autowired

  private MessageService service;

  //  @Autowired
  //  public MyApplication(MessageService svc) {
  //    service = svc;
  //  }
  //
  @Autowired
  @Qualifier("emailService")
  public void setService(MessageService svc) {
    service = svc;
  }

  public boolean processMessage(String msg, String rec) {
    // some magic like validation, logging etc
    return service.sendMessage(msg, rec);
  }
}
