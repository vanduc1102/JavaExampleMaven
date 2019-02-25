package my.example.spring.service;

import org.springframework.stereotype.Service;

@Service("emailService")
public class EmailService implements MessageService {

  @Override
  public boolean sendMessage(String msg, String rec) {
    System.out.println("Email Sent to " + rec + " with Message=" + msg);
    return true;
  }
}
