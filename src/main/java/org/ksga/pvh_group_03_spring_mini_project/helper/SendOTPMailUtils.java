package org.ksga.pvh_group_03_spring_mini_project.helper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Configuration
@RequiredArgsConstructor
public class SendOTPMailUtils {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public void sendOtpEmail(String email, Integer Otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        Context context = new Context();
        context.setVariable("email", email);
        context.setVariable("Otp", Otp);
        String htmlContent = templateEngine.process("otp-email", context);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify your OTP");
        mimeMessageHelper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);
    }


}