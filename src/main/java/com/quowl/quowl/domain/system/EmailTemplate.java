package com.quowl.quowl.domain.system;

import com.quowl.quowl.domain.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class EmailTemplate extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 65535, nullable = false)
    @Lob
    private String body;

    @Column(nullable = false)
    private String subject;

    @Column
    private String context;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
