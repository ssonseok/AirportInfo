package com.airportinfo.util;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Properties;

/**
 * Sending Email with attachments
 *
 * @author ShinHeeYoun
 */
public class EmailManager {

    private static final String USER = "shson1849@gmail.com";
    private static final String PASSWORD = "dlxh sjwr aayc opdc";

    public static void send(String email, String content, String filePath) throws MessagingException, UnsupportedEncodingException {
        /*
         * put SMTP server information in Property
         * 1) host : gmail.com
         * 2) port number : 465 (google)
         * 3) using SMTP auth : true
         * 4) using SSL : true
         * 5) SSL trust : true
         */
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        System.out.println("Succeed : Connection to Google SMTP Mail Server");
        
        /*
         * make Instance of Session class with SMTP server information
         * 
         */
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASSWORD);
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USER));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        
        /*
         * Subject
         * content
         */
        message.setSubject("MJU Airport information");
    	/*
    	 * Attachment part
    	 * divide 'text part' and 'attachment part' with MimeBodyPart object
    	 * fds: file path for attachment file
    	 * 
    	 */
    	if (Objects.equals(filePath, "NONE")) {
    		message.setText(content);
        }
    	else {
            MimeBodyPart attachPart = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(filePath);
            attachPart.setDataHandler(new DataHandler(fds));
            attachPart.setFileName(MimeUtility.encodeText(fds.getName(), "euc-kr", "B"));
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html;charset=euc-kr");

            /*
             * Combine two divided part(text+attachment) by using Multipart object
             */
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);
            /*
             *  Send message by using Transport class
             */

        }
        Transport.send(message);
    }
}
