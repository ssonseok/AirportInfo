package com.airportinfo.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * Sending Email with attachments
 * 
 * @author ShinHeeYoun
 *
 */
public class EmailManager {

    private static final String USER = "MJUairportFile@gmail.com";
    private static final String PASSWORD = "kpflpgpvgvxxzcxh";
    
	public static void send(String email, String filePath) throws AddressException, MessagingException, UnsupportedEncodingException {
    	String recipient = email;	
    	/** 
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
        
        /**
         * make Instance of Session class with SMTP server information
         * (SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성)
         * 
         */
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASSWORD);
            }
        });
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USER));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        
        /**
         * Subject(제목) -> setSubject
         * content(내용) 
         */
        message.setSubject("고급객체지향 공항 정보");
    	String content = "이 메일은 명지대 고급객체지향 프로그래밍 프로젝트에서 구현된 메일 전송 기능으로,"
        		+ " 엑셀 파일에서 추출된 정보를 첨부파일로 받아올 수 있습니다.";
    	
    	/**
    	 * Attachment(첨부파일) part
    	 * divide 'text part' and 'attachment part' with MimeBodyPart object
    	 * fds: file path for attachment file
    	 * 
    	 */
        MimeBodyPart attachPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(filePath);
        attachPart.setDataHandler(new DataHandler(fds));
        attachPart.setFileName(MimeUtility.encodeText(fds.getName(), "euc-kr","B"));
        MimeBodyPart bodypart = new MimeBodyPart();
        bodypart.setContent(content, "text/html;charset=euc-kr");

        /**
         * Combine two divided part(text+attachment) by using Multipart object
         */
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodypart);
        multipart.addBodyPart(attachPart);
        message.setContent(multipart);
        /**
         *  Send message by using Transport class
         */
        Transport.send(message);
        System.out.println("Send Complete");
	}

}
