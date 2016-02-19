package com.quowl.quowl.web.controllers.account.validation;


import org.apache.commons.lang3.StringUtils;

public class StatusValidate {

    public static void validate(String book, String author) {
        if (StringUtils.isBlank(book)) {
            book = null;
        }
        if (StringUtils.isBlank(author)) {
            author = null;
        }
    }

}
