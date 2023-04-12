/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author k
 */
public class HelperV2 {


//mailer
public static void sendWelcomeEmail(String toEmail,String context) throws MessagingException {
    // Set up mail server properties
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    // Create a mail session with authentication
    Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("athlontn@gmail.com", "vbmcqlujlyxnftax");
        }
    };
    Session session = Session.getInstance(props, auth);

    // Create a new message
    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress( "athlontn@gmail.com" ) );
    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
    msg.setSubject("Welcome to our app!");
    msg.setText(context);
//    msg.setText("Dear new user,\n\nWelcome to our app! We are glad you signed up.");

    // Send the message
    Transport.send(msg);
}




public static void sendVariableEmail(String toEmail,String context,String title) throws MessagingException {
    // Set up mail server properties
    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    // Create a mail session with authentication
    Authenticator auth = new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("athlontn@gmail.com", "vbmcqlujlyxnftax");
        }
    };
    Session session = Session.getInstance(props, auth);

    Message msg = new MimeMessage(session);
    msg.setFrom(new InternetAddress( "athlontn@gmail.com" ) );
    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
    msg.setSubject(title);
    msg.setText(context);
    Transport.send(msg);
}
    
    
}



