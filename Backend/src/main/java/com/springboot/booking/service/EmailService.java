package com.springboot.booking.service;

import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.BException;
import com.springboot.booking.model.EmailDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendSimpleMail(EmailDetail details) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(details.getRecipient());
        mailMessage.setText(details.getMsgBody());
        mailMessage.setSubject(details.getSubject());

        javaMailSender.send(mailMessage);
    }

    public void sendHtmlEmail(EmailDetail emailDetail) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailDetail.getRecipient());
        helper.setSubject(emailDetail.getSubject());
        helper.setText(emailDetail.getMsgBody(), true);

        javaMailSender.send(message);
    }
}
