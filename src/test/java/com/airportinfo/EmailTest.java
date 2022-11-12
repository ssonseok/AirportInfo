package com.airportinfo;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import com.airportinfo.utils.EmailManager;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
/**
 * Controller handling Email function (src/main/java/com.airportinfo/EmailManager.java)
 * 
 * @author ShinHeeYoun
 *
 */
public class EmailTest {
	public static void main(String[] args) {
		try {
			String Email;
			String fileDir;
			Scanner sc = new Scanner(System.in);
			
			System.out.print("전송 받을 이메일을 입력하세요 : ");
			Email = sc.nextLine();
			System.out.print("파일 경로를 입력하세요 : ");
			fileDir = sc.nextLine();
			sc.close();
			
			EmailManager.send(Email, fileDir);
		} catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
	}
}
