/**
 * @author - Chamath_Wijayarathna
 * Date :6/30/2022
 */


package com.example.demo.business.impl;

import com.example.demo.codeauth.CustomOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//////////////////////////////////////////////////////////////////////////////////////
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // statement that helps Identify whether the user signed in via Google or Facebook
        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2User user =  super.loadUser(userRequest);
        return new CustomOAuth2User(user, clientName);
    }//////////////////////////////////////////////////////////////////////////////////////

}