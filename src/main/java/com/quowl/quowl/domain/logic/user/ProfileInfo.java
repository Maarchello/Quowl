package com.quowl.quowl.domain.logic.user;

import com.quowl.quowl.domain.base.BaseEntity;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profileinfo")
public class ProfileInfo extends BaseEntity {

    @Size(max = 50)
    @Column(name = "firstName", nullable = true, length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "lastName", nullable = true, length = 50)
    private String lastName;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profileInfo", cascade = CascadeType.ALL)
    private User user;

    public ProfileInfo() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
