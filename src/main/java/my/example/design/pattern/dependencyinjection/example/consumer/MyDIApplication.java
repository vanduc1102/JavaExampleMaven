package my.example.design.pattern.dependencyinjection.example.consumer;

import my.example.design.pattern.dependencyinjection.example.MessageService;

public class MyDIApplication implements Consumer {

  private MessageService service;

  public MyDIApplication(MessageService svc) {
    this.service = svc;
  }

  @Override
  public void processMessages(String msg, String rec) {
    // do some msg validation, manipulation logic etc
    this.service.sendMessage(msg, rec);
  }
}
