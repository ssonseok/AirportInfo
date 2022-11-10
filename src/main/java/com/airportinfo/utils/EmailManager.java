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
    // 1. 발신자의 메일 계정과 비밀번호 설정함
    private static final String USER = "MJUairportFile@gmail.com";
    private static final String PASSWORD = "kpflpgpvgvxxzcxh";

	
	public static void send(String email, String filePath) throws AddressException, MessagingException, UnsupportedEncodingException {
		/* 일단은 스캐너 쓰고 gui는 이따가
    	Scanner sc = new Scanner(System.in);
    	System.out.print("Enter your Email : ");
        String recipient = sc.nextLine();
        sc.close();
    	 */
    	String recipient = email;
    	

        // 2. Property에 SMTP 서버 정보 설정
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
 
        // 3. SMTP 서버정보와 사용자 정보를 기반으로 Session 클래스의 인스턴스 생성
        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USER, PASSWORD);
            }
        });
 
        // 4. Message 클래스의 객체를 사용하여 수신자와 내용, 제목의 메시지를 작성한다.
       
        // 5. Transport 클래스를 사용하여 작성한 메세지를 전달한다
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(USER));
        
        // 수신자 메일 주소
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

        // 제목
        message.setSubject("고급객체지향 공항 정보");
        // 내용
    	String content = "이 메일은 명지대 고급객체지향 프로그래밍 프로젝트에서 구현된 메일 전송 기능으로,"
        		+ " 엑셀 파일에서 추출된 정보를 첨부파일로 받아올 수 있습니다.";
    	String attachment = "html";
    	
    	// 첨부파일 파트 (합치는 과정 필요)
        MimeBodyPart attachPart = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(filePath);
        attachPart.setDataHandler(new DataHandler(fds));
        attachPart.setFileName(MimeUtility.encodeText(fds.getName(), "euc-kr","B"));
        MimeBodyPart bodypart = new MimeBodyPart();
        bodypart.setContent(content, "text/html;charset=euc-kr");
       
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodypart);
        multipart.addBodyPart(attachPart);
       
        message.setContent(multipart);
        Transport.send(message);
        /*
        Transport.send(message);
        */
        
        System.out.println("Send Complete");
	}

}
