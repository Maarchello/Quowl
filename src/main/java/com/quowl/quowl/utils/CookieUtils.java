package com.quowl.quowl.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

public class CookieUtils {

    public final static String VISITOR_ID_COOKIE_NAME = "vuid";

    public static void setAuthCookie(HttpServletResponse response, String value) {
        // set cookie
        Cookie cookie = new Cookie(XAuthTokenFilter.XAUTH_TOKEN_COOKIE_NAME, value);
        cookie.setHttpOnly(false);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
    }

    public static void removeAuthCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(XAuthTokenFilter.XAUTH_TOKEN_COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
    }

    public static void setVisitorCookie(HttpServletResponse response, String value) {
        Cookie cookie = new Cookie(VISITOR_ID_COOKIE_NAME, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(-1); // deleted after browser exit
        response.addCookie(cookie);
    }

    public static String getCookieValueByName(String cookieName, HttpServletRequest httpServletRequest) {
        Cookie[] userCookies = httpServletRequest.getCookies() == null ? new Cookie[0] : httpServletRequest.getCookies();
        Optional<Cookie> authCookie = Arrays.stream(userCookies)
                .filter(cookie -> {return cookie.getName().equalsIgnoreCase(cookieName);})
                .findFirst();
        return authCookie.map(cookie -> {return cookie.getValue();}).orElse(null);
    }

}
