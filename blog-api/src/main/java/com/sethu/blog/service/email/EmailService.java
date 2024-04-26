package com.sethu.blog.service.email;


import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class EmailService {

    private static final Logger logger = LogManager.getLogger(EmailService.class);


    private JavaMailSender javaMailSender;

    public void sendEmail(String to, String subject,String recipientName, String body) {
        var  message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            String personalizedBody = String.format("Dear %s,%n%n%s", recipientName, body);
            helper.setText(personalizedBody, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            logger.error("Error Occurred while Sending Email: "+e.getMessage());
        }

        logger.info("Email has been sent "+" to "+recipientName+" at "+to+" date: "+ new Date());
    }
}
