package com.quowl.quowl.service.signinup;


import com.quowl.quowl.service.user.CustomUserDetailsService;
import com.quowl.quowl.utils.CookieUtils;
import com.quowl.quowl.service.system.TokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecurityService {

    @Inject private AuthenticationManager authenticationManager;
    @Inject private CustomUserDetailsService detailsService;
    @Inject private TokenProvider tokenProvider;

    public void login(String name, String password, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(name, password);
        Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = detailsService.loadUserByUsername(name);
        String newToken = tokenProvider.createToken(userDetails);
        CookieUtils.setAuthCookie(response, newToken);

    }

    public void logout(HttpServletResponse response) {
        SecurityContextHolder.getContext().setAuthentication(null);
        CookieUtils.removeAuthCookie(response);
    }

}
