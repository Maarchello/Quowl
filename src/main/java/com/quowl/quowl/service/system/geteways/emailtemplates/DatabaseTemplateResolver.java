package com.quowl.quowl.service.system.geteways.emailtemplates;


import com.quowl.quowl.repository.system.EmailTemplateRepository;
import org.thymeleaf.exceptions.ConfigurationException;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

public class DatabaseTemplateResolver extends TemplateResolver {

    private DatabaseResourceResolver resolver;

    public DatabaseTemplateResolver() {
        super();
        resolver = new DatabaseResourceResolver();
        super.setResourceResolver(resolver);
    }

    public void setRepository(EmailTemplateRepository emailTemplateRepository) {
        resolver.setRepository(emailTemplateRepository);
    }

    @Override
    public void setResourceResolver(final IResourceResolver resourceResolver) {
        throw new ConfigurationException(
                "Cannot set a resource resolver on " + this.getClass().getName() + ". If " +
                        "you want to set your own resource resolver, use " + TemplateResolver.class.getName() +
                        "instead");
    }

}
