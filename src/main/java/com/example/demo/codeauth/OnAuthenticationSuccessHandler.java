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
      String givenName = String.valueOf(oauth2User.getAttributes().get(2));
      String lastName = String.valueOf(oauth2User.getAttributes().get(1));
      String password = "";
    //  System.out.println("AAAAAAAAAAAAAAAAAAAA "+username +" SSSSSSSSSSSSSSSSS "+name +"  "+nnn+" asas "+ddsd);

      String role = String.valueOf(AppUserRoleEnum.ROLE_USER);
//String firstName, String lastName, String email, String password, AppUserRoleEnum appUserRoleEnum

      userService.updateAuthenticationType(new AppUser(givenName,name,username,AppUserRoleEnum.ROLE_USER  , AuthenticationType.GOOGLE));

      super.onAuthenticationSuccess(request, response, authentication);
   }

}