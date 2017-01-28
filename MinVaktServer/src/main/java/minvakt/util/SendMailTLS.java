package minvakt.util; /**
 * Created by klk94 on 19.01.2017.
 */
import minvakt.datamodel.tables.pojos.Employee;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class SendMailTLS {
    Session session;

    public SendMailTLS() {
        final String username = "kennyboy083@gmail.com", password = "!\"#123qweasd";

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

    private void sendMail(Message message) {
        try {
            Transport.send(message);
        } catch (MessagingException ignored) {
        }
    }

    private void buildMessage(Message m, String email, String subj, String text) {
        try {
            m.setFrom(new InternetAddress("noreply@minvakt.123"));
            m.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            m.setSubject(subj);
            m.setText(text);
        } catch (MessagingException ignored) {

        }
    }

    public void sendPassword(String email, String password) {
        Message message = new MimeMessage(session);
        String subject = "Minvakt passord", text = ("Hei\n" + "Her er ditt passord: " + password + "\n\nhttps://minvakt.herokuapp.com");
        buildMessage(message, email, subject, text);
        sendMail(message);
    }

    public void sendFreeShift(String link, String email) {
        Message message = new MimeMessage(session);
        String subject = "Minvakt har en ny vakt ledig", text = ("Hei\n" + "Her er en link til vakten: " + link);
        buildMessage(message, email, subject, text);
        sendMail(message);
    }

    public void sendFreeShiftToGroup(String link, List<Employee> eMails) {
        for(Employee one:eMails) {
            sendFreeShift(link, one.getEmail());
        }
    }

    public void sendAnswerOnShiftChange(String email, String answer) {
        Message message = new MimeMessage(session);
        String subject = "Minvakt vaktbytte", text = ("Hei\n" + answer + "\nhttps://minvakt.herokuapp.com");
        buildMessage(message, email, subject, text);
        sendMail(message);
    }

    public void sendChangeRequstToAdmin(String email,String text) {
        Message message = new MimeMessage(session);
        String subject = "Minvakt bruker ønsker å bytte vakt", text1 = text + "\nhttps://minvakt.herokuapp.com";
        buildMessage(message, email, subject, text1);
        sendMail(message);
    }

    public void sendMessageAbsent(String email, String text) {
        Message message = new MimeMessage(session);
        String subject = "Minvakt bruker ønsker å bytte vakt", text1 = text + "\nhttps://minvakt.herokuapp.com";
        buildMessage(message, email, subject, text1);
        sendMail(message);
    }

    public void sendTotalHoursToThePayrollOffice(String email,String text) {
        Message message = new MimeMessage(session);
        String subject = "Timelisten for Minvakt";
        buildMessage(message, email, subject, text);
        sendMail(message);
    }
}