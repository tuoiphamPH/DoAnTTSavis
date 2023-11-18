package com.example.test_spring_boot.Configuration.Security;

import com.example.test_spring_boot.Entity.RoleEntity;
import com.example.test_spring_boot.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UserDetailCustom implements org.springframework.security.core.userdetails.UserDetails{

    @Autowired
    UserEntity userEntity;
    @Autowired
    HttpServletResponse response;


    public UserDetailCustom(UserEntity user) throws IOException {
       if(user == null){
           response.sendRedirect("/login");
       }else {
           this.userEntity = user;
       }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>(userEntity.getRoles().size());
        for(RoleEntity role : userEntity.getRoles()){
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    public Long getId() {
        return userEntity.getId();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
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
