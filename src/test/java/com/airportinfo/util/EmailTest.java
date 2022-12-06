package com.airportinfo.util;

import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Controller handling Email function (src/main/java/com.airportinfo/EmailManager.java)
 *
 * @author ShinHeeYoun
 */
public class EmailTest {
    @Test
    public void testEmail() {
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

        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
