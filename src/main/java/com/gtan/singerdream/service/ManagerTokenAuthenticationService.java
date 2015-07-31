package com.gtan.singerdream.service;

import com.gtan.singerdream.model.Manager;
import com.gtan.singerdream.model.security.ManagerAuthentication;
import com.gtan.singerdream.util.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

/**
 * Created by user on 15-7-30.
 */
@Service
public class ManagerTokenAuthenticationService {
    private static final String MANAGER_AUTH_HEADER_NAME = "MX-AUTH-TOKEN";
    private static final int TEN_DAY = 60 * 60 * 24 * 10;

    private final TokenHandler tokenHandler;

    @Autowired
    public ManagerTokenAuthenticationService(@Value("${token.secret}") String secret) {
        this.tokenHandler = new TokenHandler(DatatypeConverter.parseBase64Binary(secret));
    }

    public void addManagerAuthentication(HttpServletResponse response, ManagerAuthentication authentication) {
        final Manager manager = authentication.getDetails();
        String token = tokenHandler.createTokenFor(manager);
        Cookie cookie = new Cookie(MANAGER_AUTH_HEADER_NAME, token);
        cookie.setMaxAge(TEN_DAY);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private String getManagerCookieFromHeader(HttpServletRequest request) {
        return request.getHeader(MANAGER_AUTH_HEADER_NAME);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        Cookie tokenCookie = null;
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(MANAGER_AUTH_HEADER_NAME)) {
                    tokenCookie = cookie;
                    if (tokenCookie == null) {
                        return null;
                    } else if (!tokenCookie.getValue().isEmpty()) {
                        token = tokenCookie.getValue();
                    } else {
                        String headerToken = getManagerCookieFromHeader(request);
                        if (headerToken == null || headerToken.isEmpty()) {
                            return null;
                        } else {
                            token = headerToken;
                        }
                    }
                    break;
                }
            }
        } else if (request.getHeader(MANAGER_AUTH_HEADER_NAME) != null) {
            token = request.getHeader(MANAGER_AUTH_HEADER_NAME);
        } else {
            return null;
        }

        if (token == null || token.isEmpty()) {
            return null;
        } else {
            Manager manager = tokenHandler.parseManagerFromToken(token);
            return new ManagerAuthentication(manager);
        }
    }

}
