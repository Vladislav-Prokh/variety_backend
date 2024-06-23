package com.onlineShop.store.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.onlineShop.store.entities.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public String sendRegistrationConfirmationEmail(User user) throws MessagingException {
        String token = generateConfirmationToken(user);
        MimeMessage message = createConfirmationEmail(user, token);
        mailSender.send(message);
        return token;
    }

    private String generateConfirmationToken(User user) {
    	  String tokenData = user.getId() + user.getEmail() + RandomStringUtils.randomAlphanumeric(10);
          String token = null;
          try {
              MessageDigest digest = MessageDigest.getInstance("SHA-256");
              byte[] hash = digest.digest(tokenData.getBytes(StandardCharsets.UTF_8));
              token = bytesToHex(hash);
          } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          }
          return token;
    }
    
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    private MimeMessage createConfirmationEmail(User user, String token) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress("noreply@example.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
        } catch (MessagingException e) {
        }

        message.setSubject("Confirm Your Registration");
        message.setText("Please click on the following link to confirm your registration:\n" +
                "http://85.217.171.56:8086/confirm-email/success/"+token);

        return message;
    }
}
