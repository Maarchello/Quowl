package com.quowl.quowl.web.beans.user;


import com.quowl.quowl.domain.logic.user.Gender;
import com.quowl.quowl.domain.logic.user.ProfileInfo;
import com.quowl.quowl.web.beans.system.WebBean;
import org.joda.time.LocalDate;

/**
 * The DTO layer under {@link ProfileInfo} entity.
 * It is used to get only needed fields without
 * accessing DB. It is also better to send it to
 * client side instead of ProfileInfo entity.
 * Use {@link #copyDataFromDomain(ProfileInfo)} to
 * copy fields from entity to ProfileBean object or
 * {@link #copyDataFromDomain(Object)} backwards.
 *
 * @see WebBean
 *
 */
public class ProfileBean implements WebBean<ProfileInfo> {

    private long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate birthday;

    public ProfileBean() {
    }

    public ProfileBean(ProfileInfo profileInfo) {
        this.firstName = profileInfo.getFirstName();
        this.lastName = profileInfo.getLastName();
        this.gender = profileInfo.getGender();
        this.birthday = profileInfo.getBirthDate();
        this.id = profileInfo.getId();
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @param obj the entity object to copy from.
     */
    @Override
    public void copyDataFromDomain(ProfileInfo obj) {
        id = obj.getId();
        birthday = obj.getBirthDate();
        gender = obj.getGender();
        firstName = obj.getFirstName();
        lastName = obj.getLastName();
    }

    /**
     *
     * @param obj the entity object to copy in.
     */
    @Override
    public void copyDataToDomain(ProfileInfo obj) {
        obj.setId(id);
        obj.setBirthDate(birthday);
        obj.setFirstName(firstName);
        obj.setGender(gender);
        obj.setLastName(lastName);
    }
}
