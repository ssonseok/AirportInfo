package com.airportinfo.utils;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * Sending Email with attachments
 * 
 * @author ShinHeeYoun
 */
public class EmailManager {

    private static final String USER = "MJUairportFile@gmail.com";
    private static final String PASSWORD = "kpflpgpvgvxxzcxh";
    
	public static void send(String email, String filePath) throws MessagingException, UnsupportedEncodingException {
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
        System.out.println("Succeed : Destination Email (" + email + ") exist");
        
        /*
         * Subject
         * content
         */
        message.setSubject("MJU Airport information");
    	String content = "This email was sent by using the Email sending function which is developed by team project of the advanced object-oriented class."
    			+ "\nYou can send attachments with mail by entering the absolute path of the file which you want to send.";
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
            attachPart.setFileName(MimeUtility.encodeText(fds.getName(), "euc-kr","B"));
            MimeBodyPart bodypart = new MimeBodyPart();
            bodypart.setContent(content, "text/html;charset=euc-kr");

            /*
             * Combine two divided part(text+attachment) by using Multipart object
             */
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodypart);
            multipart.addBodyPart(attachPart);
            message.setContent(multipart);
            /*
             *  Send message by using Transport class
             */

        }
        System.out.println("Sending...");
        Transport.send(message);

        System.out.println("Send Complete!");
	}
}
