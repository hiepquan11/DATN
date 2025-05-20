package com.huynhduc.backend.utils.Cookie;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtils {
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly, boolean secure, String path) {
        Cookie cookie = new Cookie(name, value); // đây là Cookie của jakarta.servlet.http
        cookie.setMaxAge(maxAge);
        cookie.setHttpOnly(httpOnly);
        cookie.setSecure(secure);
        cookie.setPath(path);
        response.addCookie(cookie);
    }
}
