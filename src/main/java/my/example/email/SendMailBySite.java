package my.example.email;

/**
 *
 * @author nvduc
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendMailBySite {

    public static void main(String[] args) {

        String host = "exchange.aavn.local";
        int port = 25;
        final String user = "notifier@axonactive.com";//change accordingly  
        final String password = "xxxxx";//change accordingly  

        String to = "duc.nguyen@axonactive.com";//change accordingly  

        //Get the session object  
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        //props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        //Compose the message  
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(" send from ducnguyen in email");
            message.setText("This is simple program of sending email using JavaMail API");

            //send the message  
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
