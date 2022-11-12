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
			
			System.out.print("Email : ");
			Email = sc.nextLine();	
			while (Email.isBlank()) {
				System.out.println("Email not entered, you must Enter Email to use this function");
				System.out.print("Email : ");
				Email = sc.nextLine();
			}
			
			System.out.print("Attachment file path : ");
			fileDir = sc.nextLine();
			
			sc.close();
			
			if (fileDir.isBlank()) {
				System.out.println("\nSending Email without Attachment file...");
				fileDir = "NONE";
				EmailManager.send(Email, fileDir);
			} else {
				System.out.println("\nSending Email with Attachment file...");
				EmailManager.send(Email, fileDir);
			}
			
		} catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        } 
	}
}
