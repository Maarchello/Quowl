package com.quowl.quowl.repository.system;

import com.quowl.quowl.domain.system.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    EmailTemplate findByName(String name);

}
