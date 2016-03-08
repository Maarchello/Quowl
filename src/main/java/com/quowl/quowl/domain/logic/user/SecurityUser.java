package com.quowl.quowl.domain.logic.user;


import com.quowl.quowl.utils.AuthoritiesConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class SecurityUser extends User implements UserDetails {

    public SecurityUser(User user) {
        if (user != null) {
            this.setId(user.getId());
            this.setLangKey(user.getLangKey());
            this.setProfileInfo(user.getProfileInfo());
            this.setEmail(user.getEmail());
            this.setNickname(user.getNickname());
            this.setPassword(user.getPassword());
            this.setQuotes(user.getQuotes());
            this.setCreatedDate(user.getCreatedDate());
            this.setAuthorName(user.getAuthorName());
            this.setBookName(user.getBookName());
            this.setBooks(user.getBooks());
            this.setLikes(user.getLikes());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(AuthoritiesConstants.USER);
        authorities.add(authority);

        return authorities;
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
