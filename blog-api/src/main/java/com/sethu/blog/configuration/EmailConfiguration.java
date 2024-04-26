package com.sethu.blog.configuration;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@AllArgsConstructor
@Component
public class EmailConfiguration {

    private Properties props = new Properties();

    public EmailConfiguration() {
        try {
            props.load(getClass().getClassLoader().getResourceAsStream("email.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEmailSubject() {
        return props.getProperty("email.subject");
    }

    public String getEmailBody() {
        return props.getProperty("email.body");
    }
}
