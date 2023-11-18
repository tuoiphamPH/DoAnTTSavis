package com.example.test_spring_boot.Configuration.Oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOauth2User implements OAuth2User {
    private OAuth2User oAuth2User;
    private String access_token;
    private String clientMethod;
    private String clientName;

    public CustomOauth2User(OAuth2User oAuth2User, OAuth2UserRequest userRequest, String clientName){
        this.clientMethod = userRequest.getClientRegistration().getClientName();
        this.access_token = userRequest.getAccessToken().getTokenValue();
        this.oAuth2User = oAuth2User;
        this.clientName = clientName;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail(){
        return oAuth2User.getAttribute("email");
    }

    public String getAccess_token(){
        return access_token;
    }

    public String getClientMethod(){
        return clientMethod;
    }
    public String getClientName(){
        return clientName;
    }
}
