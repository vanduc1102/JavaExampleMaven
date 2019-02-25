package my.example.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"my.example.spring"})
class DIConfiguration {

  //  @Bean
  //  public MessageService getMessageService() {
  //    return new TwitterService();
  //  }
}
