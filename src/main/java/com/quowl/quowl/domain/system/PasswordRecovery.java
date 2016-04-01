package com.quowl.quowl.domain.system;

import com.quowl.quowl.domain.base.BaseEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Created by nllsd on 01.04.2016.
 */
@Entity
@Table(name = "password_recovery", schema = "quowl")
public class PasswordRecovery extends BaseEntity{

    private String url;
    private Long creationTime;

    @Basic
    @Column(name = "url", length = 255, nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "creationTime", nullable = false)
    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordRecovery that = (PasswordRecovery) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return creationTime != null ? creationTime.equals(that.creationTime) : that.creationTime == null;

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        return result;
    }
}
