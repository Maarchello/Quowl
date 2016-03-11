package com.quowl.quowl.web.beans.user;


import com.quowl.quowl.domain.logic.user.Gender;
import com.quowl.quowl.domain.logic.user.ProfileInfo;
import org.joda.time.LocalDate;


public class ProfileBean {

    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;

    public ProfileBean(ProfileInfo profileInfo) {
        this.firstName = profileInfo.getFirstName();
        this.lastName = profileInfo.getLastName();
        this.gender = profileInfo.getGender();
        this.birthday = profileInfo.getBirthDate();
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
