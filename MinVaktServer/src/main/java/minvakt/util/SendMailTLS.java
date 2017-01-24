package minvakt.util; /**
 * Created by klk94 on 19.01.2017.
 */
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {

    Session session;

    public SendMailTLS() {
        final String username = "kennyboy083@gmail.com";
        final String password = "!\"#123qweasd";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

/*
    public static void main(String[] args) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        final String username = "kennyboy083@gmail.com";
        final String password = "!\"#123qweasd";
        Session session;
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply@minvakt.123"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("k.l.k08394@gmail.com"));
            message.setSubject("Velkommen til MinVakt");
            message.setText("Hei\n"+
                    "Velkommen til MinVakt\n" +
                    "Her er ditt passord: "+ password +
                    "\nhttps://minvakt.herokuapp.com");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
*/

    public void sendPassword(String email, String password) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply@minvakt.123"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Minvakt passord");
            message.setText("Hei\n" +
                    "Her er ditt passord: " + password +
                    "\nhttps://minvakt.herokuapp.com");

            Transport.send(message);

            System.out.println("Email sent with password");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFreeGuard(String link, String email) {
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply@minvakt.123"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Minvakt har ny vakt ledig");
            message.setText("Hei\n" +
                    "Her er en link til shiftet: " + link);

            Transport.send(message);

            System.out.println("Email sent with the guard link");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFreeGuardToGroup(String link, String[] eMails) {
        for(String eMail:eMails) {
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("noreply@minvakt.123"));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(eMail));
                message.setSubject("Minvakt har ny vakt ledig");
                message.setText("Hei\n" +
                        "Her er en link til shiftet: " + link);

                Transport.send(message);

                System.out.println("Email sent with the guard link");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendAnswearOnGuardChange(String email, String answear) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("noreply@minvakt.123"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Minvakt shift bytte");
            message.setText("Hei\n"+
                    answear +
                    "\nhttps://minvakt.herokuapp.com"
            );

            Transport.send(message);

            System.out.println("Email sent with guard change answear");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}