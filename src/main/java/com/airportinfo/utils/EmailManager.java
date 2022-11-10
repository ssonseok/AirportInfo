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


public class EmailManager {
    private static final String USER = "MJUairportFile@gmail.com";
    private static final String PASSWORD = "kpflpgpvgvxxzcxh";
    
	public static void send(String email, String filePath) throws AddressException, MessagingException, UnsupportedEncodingException {
    	String recipient = email;
        //Property에 SMTP 서버 정보 설정
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASSWORD);
            }
        });
        //Transport 클래스를 사용하여 작성한 메세지를 전달한다
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USER));
        //수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        // 제목
        message.setSubject("고급객체지향 공항 정보");
        // 내용 ( content 값이 내용으로 설정되어 전송됨 )
    	String content = "이 메일은 명지대 고급객체지향 프로그래밍 프로젝트에서 구현된 메일 전송 기능으로,"
        		+ " 엑셀 파일에서 추출된 정보를 첨부파일로 받아올 수 있습니다.";
    	// 첨부파일 파트 (합치는 과정 필요)
        MimeBodyPart attachPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(filePath);
        attachPart.setDataHandler(new DataHandler(fds));
        attachPart.setFileName(MimeUtility.encodeText(fds.getName(), "euc-kr","B"));
        MimeBodyPart bodypart = new MimeBodyPart();
        bodypart.setContent(content, "text/html;charset=euc-kr");
        // 합치는 과정 (문자내용+첨부파일내용)
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodypart);
        multipart.addBodyPart(attachPart);
        message.setContent(multipart);
        //전송!
        Transport.send(message);
        System.out.println("Send Complete");
	}

}
