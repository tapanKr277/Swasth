package com.org.swasth_id_backend.service.serviceImpl;

import com.org.swasth_id_backend.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private boolean isEnabled;
    private boolean isVerified;
    private boolean isOtpVerified;
    private LocalDateTime otpLastUpdate;
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user){

        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.isEnabled = user.getIsActive();
        this.isVerified = user.getIsVerified();
        this.isOtpVerified = user.getIsOtpVerified();
        this.otpLastUpdate = user.getOtpLastUpdate();

        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
