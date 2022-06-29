/**
 * @author - Chamath_Wijayarathna
 * Date :6/17/2022
 */


package com.example.demo.codeauth;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.business.impl.LoginAttemptsServiceBOImpl;
import com.example.demo.business.impl.LoginAttemptsUserServicesBOImpl;
import com.example.demo.entity.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

   @Autowired
   private LoginAttemptsUserServicesBOImpl userService;

   @Override
   public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                       Authentication authentication) throws IOException, ServletException {
      System.out.println(authentication);
      AppUser user =  (AppUser) authentication.getPrincipal();
      if (user.getFailedAttempt() > 0) {
         userService.resetFailedAttempts(user.getEmail());
      }

     authSuccess(request,response,authentication);
   }

   void authSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
      System.out.println("Inside Autttttttttttth Success");
      super.onAuthenticationSuccess(request, response, authentication);
   }



}