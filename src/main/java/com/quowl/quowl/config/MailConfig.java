package com.quowl.quowl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.annotation.Resource;
import java.util.Properties;

@PropertySource("classpath:properties/main.properties")
@Configuration
public class MailConfig {


    private static final String ENV_SPRING_MAIL = "spring.mail.";
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final String PROP_HOST = "host";
    private static final String DEFAULT_PROP_HOST = "localhost";
    private static final String PROP_PORT = "port";
    private static final String PROP_USER = "user";
    private static final String PROP_PASSWORD = "password";
    private static final String PROP_PROTO = "protocol";
    private static final String PROP_TLS = "tls";
    private static final String PROP_AUTH = "auth";
    private static final String PROP_SMTP_AUTH = "mail.smtp.auth";

    private static final String PROP_STARTTLS = "mail.smtp.starttls.enable";
    private static final String PROP_TRANSPORT_PROTO = "mail.transport.protocol";

    @Resource
    private Environment env;


    @Bean
    public JavaMailSenderImpl javaMailSender() {
        String host = env.getProperty(PROP_HOST, DEFAULT_PROP_HOST);
        int port = env.getProperty(PROP_PORT, Integer.class, 0);
        String user = env.getProperty(PROP_USER);
        String password = env.getProperty(PROP_PASSWORD);
        String protocol = env.getProperty(PROP_PROTO);
        Boolean tls = env.getProperty(PROP_TLS, Boolean.class, false);
        Boolean auth = env.getProperty(PROP_AUTH, Boolean.class, false);

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        if (host != null && !host.isEmpty()) {
            sender.setHost(host);
        } else {
            sender.setHost(DEFAULT_HOST);
        }
        sender.setPort(port);
        sender.setProtocol(protocol);
        sender.setUsername(user);
        sender.setPassword(password);

        Properties sendProperties = new Properties();
        sendProperties.setProperty(PROP_SMTP_AUTH, auth.toString());
        sendProperties.setProperty(PROP_STARTTLS, tls.toString());
        sendProperties.setProperty("mail.smtp.ssl.trust", "*");
        sender.setJavaMailProperties(sendProperties);
        return sender;
    }

}
