package com.quowl.quowl.domain.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
public abstract class AuditingEntity extends BaseEntity {

    @Column(name = "createdDate", nullable = false)
    private Date createdDate = new Date();


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
