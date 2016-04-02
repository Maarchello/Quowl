package com.quowl.quowl.config;

import com.quowl.quowl.service.system.XAuthTokenConfigurer;
import com.quowl.quowl.service.user.CustomUserDetailsService;
import com.quowl.quowl.service.system.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Inject private TokenProvider tokenProvider;

    @Inject private UserDetailsService userDetailsService;

    @Inject private DefaultEntryPoint defaultEntryPoint;

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new CustomUserDetailsService();
    }
    @Inject
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private XAuthTokenConfigurer securityConfigurerAdapter() {
        return new XAuthTokenConfigurer(userDetailsService, tokenProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .exceptionHandling()
                .authenticationEntryPoint(defaultEntryPoint)
        .and()
                .apply(securityConfigurerAdapter())
        .and()
              .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/login", "/logout", "/signup").permitAll()
                .antMatchers("/access/**").permitAll()
                .antMatchers("/precovery").permitAll()
                .antMatchers("/feed").authenticated()
                .antMatchers("/settings").authenticated()
                .antMatchers("/{nickname}").permitAll()
                .anyRequest().authenticated();

    }

    @Bean
    public TokenProvider tokenProvider(){
        String secret = "secret";
        int validityInSeconds = 172800;
        return new TokenProvider(secret, validityInSeconds, 172800); //2 дня
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/favicon.ico")
                .antMatchers("/resources/**")
                .antMatchers("/userresources/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
