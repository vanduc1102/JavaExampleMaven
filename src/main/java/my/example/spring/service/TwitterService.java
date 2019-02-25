package my.example.spring.service;

import org.springframework.stereotype.Service;

@Service("twitterService")
public class TwitterService implements MessageService {

  @Override
  public boolean sendMessage(String msg, String rec) {
    System.out.println("Twitter message Sent to " + rec + " with Message=" + msg);
    return true;
  }
}
