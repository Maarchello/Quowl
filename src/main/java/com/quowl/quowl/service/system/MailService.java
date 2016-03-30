package com.quowl.quowl.service.system;

import com.quowl.quowl.repository.system.EmailTemplateRepository;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PropertySource("classpath:properties/db.properties")
@Service
public class MailService {

    private static Pattern removeTags = Pattern.compile("<.+?>");

    @Inject private JavaMailSenderImpl javaMailSender;

    @Resource
    private Environment env;

    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private EmailTemplateRepository emailTemplateRepository;

    private String from;

    private String appBaseUrl;

    public String getFrom() {
        return from;
    }

    public String getAppBaseUrl() {
        return appBaseUrl;
    }

    @PostConstruct
    public void init() {
        from = env.getRequiredProperty("from");
        appBaseUrl = env.getRequiredProperty("baseUrl");
    }

    @Async
    public void sendEmail(String templateName, String email, Context context) {
        String body = templateEngine.process("mails." + templateName + ".body", context);
        String subject = templateEngine.process("mails." + templateName + ".subject", context);

        Matcher matcher = removeTags.matcher(subject);
        subject = matcher.replaceAll("");


        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            boolean isMultipart = false;
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8);
            message.setTo(email);
            message.setFrom(from);
            message.setSubject(subject);
            message.setText(body, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            System.out.println("log");
        }
    }


}
