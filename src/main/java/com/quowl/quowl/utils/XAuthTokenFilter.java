package com.quowl.quowl.utils;

import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XAuthTokenFilter extends GenericFilterBean{

    public final static String XAUTH_TOKEN_COOKIE_NAME = "x-auth-token";

    private UserDetailsService detailsService;

    private TokenProvider tokenProvider;

    public XAuthTokenFilter(UserDetailsService detailsService, TokenProvider tokenProvider) {
        this.detailsService = detailsService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            String authToken = CookieUtils.getCookieValueByName(XAUTH_TOKEN_COOKIE_NAME, httpServletRequest);
            if (StringUtils.hasText(authToken)) {
                boolean isValidated = false;
                String username = tokenProvider.getUserNameFromToken(authToken);
                UserDetails details = null;
                if(!StringUtils.isEmpty(username)) {
                    try {
                        details = detailsService.loadUserByUsername(username);
                    } catch (Exception e) {}

                }
                if(details != null) {
                    if(tokenProvider.validateTokenSignature(authToken, details)) {
                        // signature OK, checking expires
                        if(tokenProvider.validateTokenExpire(details, response, authToken)) {
                            isValidated = true;
                        }
                    }
                }

                if(details != null && isValidated) {
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, details.getPassword(), details.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(token);
                    MDC.put("user", username);
                } else {
                    CookieUtils.removeAuthCookie((HttpServletResponse)servletResponse);
                }

            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
