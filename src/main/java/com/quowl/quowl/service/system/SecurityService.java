/*
package com.quowl.quowl.service.system;

import com.quowl.quowl.utils.CookieUtils;
import com.quowl.quowl.utils.TokenProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecurityService {

    @Inject private AuthenticationManager authenticationManager;


    @Inject @Qualifier("customUserDetails") private UserDetailsService userDetailsService;

    @Inject private TokenProvider tokenProvider;

    public void login(String username, String password, HttpServletResponse servletResponse) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails details = this.userDetailsService.loadUserByUsername(username);
        String newToken = tokenProvider.createToken(details);
        CookieUtils.setAuthCookie(servletResponse, newToken);

    }

}
*/
