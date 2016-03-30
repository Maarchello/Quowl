package com.quowl.quowl.service.system.geteways.emailtemplates;


import com.quowl.quowl.domain.system.EmailTemplate;
import com.quowl.quowl.repository.system.EmailTemplateRepository;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DatabaseResourceResolver implements IResourceResolver {

    private static final String NAME = "DBRESOLVER";

    private EmailTemplateRepository emailTemplateRepository;

    public DatabaseResourceResolver() { super(); }

    public void setRepository(EmailTemplateRepository emailTemplateRepository) {
        this.emailTemplateRepository = emailTemplateRepository;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
        String name = StringUtils.substringBetween(resourceName, ".");
        EmailTemplate emailTemplate = emailTemplateRepository.findByName(name);

        String replaceMailsSlash = resourceName.replace("mails/", "");
        String replaceMailsDot = replaceMailsSlash.replace("mails.", "");
        String replaceHtml = replaceMailsDot.replace(".html", "");

        if (replaceHtml.contains("body")) {
            return new ByteArrayInputStream(emailTemplate.getBody().getBytes(StandardCharsets.UTF_8));
        } else {
            String subjectExpression = emailTemplate.getSubject();
            String subject = "<p th:text=" + "\"" + subjectExpression + "\">" + "</p>";
            return new ByteArrayInputStream(subject.getBytes(StandardCharsets.UTF_8));
        }
    }
}
