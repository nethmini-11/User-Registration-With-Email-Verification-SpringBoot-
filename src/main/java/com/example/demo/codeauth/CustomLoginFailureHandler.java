/**
 * @author - Chamath_Wijayarathna
 * Date :6/17/2022
 */


package com.example.demo.codeauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.business.impl.LoginAttemptsUserServicesBOImpl;
import com.example.demo.entity.AppUser;
import com.example.demo.security.config.WebSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

   @Autowired
   private LoginAttemptsUserServicesBOImpl userService;

   @Override
   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                       AuthenticationException exception) throws IOException, ServletException {
      boolean validate = true;
      String email = request.getParameter("email");
      AppUser user = userService.getByEmail(email);



      if (user != null) {
         if (user.isEnabled() && user.isAccountNonLocked()) {
            if (user.getFailedAttempt() < LoginAttemptsUserServicesBOImpl.MAX_FAILED_ATTEMPTS - 1) {
               userService.increaseFailedAttempts(user);
            } else {
               user.setLocked(true);
               userService.lock(user);
               exception = new LockedException("Your account has been locked due to 3 failed attempts."
                       + " It will be unlocked after 24 hours.");
            }
         } else if (!user.isAccountNonLocked()) {
            if (userService.unlockWhenTimeExpired(user)) {
               super.setAllowSessionCreation(true);
               System.out.println(user.getUsername());
//               new CustomLoginSuccessHandler().onAuthenticationSuccess(request,response,null);
              // SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();

               validate =false;

               exception = new LockedException("Your account has been unlocked. Please try to login again.");
            }
         }

      }

    //  super.setDefaultFailureUrl("/login?error");
      if (validate)  super.onAuthenticationFailure(request, response, exception);
   }

}