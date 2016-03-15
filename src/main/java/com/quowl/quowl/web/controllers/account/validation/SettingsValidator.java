package com.quowl.quowl.web.controllers.account.validation;

import com.quowl.quowl.domain.logic.user.Gender;
import com.quowl.quowl.web.beans.user.ProfileBean;
import org.joda.time.LocalDate;

/**
 * This class is provided to validate
 * user settings.
 *
 * @see #isValid(ProfileBean)
 * @see ProfileBean
 *
 * @author nllsdfx
 */
public final class SettingsValidator {

    private SettingsValidator() {
    }

    /**
     * Checks whether user profile settings are correct.
     *
     * @param bean bean to check.
     * @return <code>true</code> if settings are correct,
     * <code>false</code> otherwise.
     */
    public static boolean isValid(ProfileBean bean) {

        boolean isCorrect = false;

        if (bean == null) {
            return isCorrect;
        }

        String firstName = bean.getFirstName();
        String lastName = bean.getLastName();

        boolean isFirstNameValid = isStringValid(firstName);
        boolean isLastNameValid = isStringValid(lastName);

        isCorrect = isFirstNameValid && isLastNameValid;

        return isCorrect;
    }

    /**
     * Checks whether the given string is null or empty.
     *
     * @param string string to check.
     * @return <code>true</code> if string is not null or empty,
     * <code>false</code> otherwise.
     */
    private static boolean isStringValid(String string) {

        return string != null && !string.isEmpty();
    }


}
