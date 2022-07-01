/**
 * @author - Chamath_Wijayarathna
 * Date :6/30/2022
 */


package com.example.demo.codeauth;

import com.example.demo.business.impl.LoginAttemptsUserServicesBOImpl;
import com.example.demo.entity.AppUser;
import com.example.demo.enumpackage.AppUserRoleEnum;
import com.example.demo.enumpackage.AuthenticationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
@Component
public class OnAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

   @Autowired
   private LoginAttemptsUserServicesBOImpl userService;

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws ServletException, IOException {
      CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

      String oauth2ClientName = oauth2User.getOauth2ClientName();
      String username = oauth2User.getEmail();
      String name = oauth2User.getName();
      String fName = String.valueOf(oauth2User.getAttributes().get(1));


      userService.updateAuthenticationType(new AppUser(fName,name,username,AppUserRoleEnum.ROLE_USER, getAuth(oauth2ClientName)));

      super.onAuthenticationSuccess(request, response, authentication);
   }
   public static AuthenticationType getAuth(String oauth2ClientName) {
      for(AuthenticationType auth : AuthenticationType.values()) {
         String aut = String.valueOf(auth);
         if(aut.equals(oauth2ClientName)) {
            return auth;
         }
      }
      return null; //Or thrown exception
   }
}