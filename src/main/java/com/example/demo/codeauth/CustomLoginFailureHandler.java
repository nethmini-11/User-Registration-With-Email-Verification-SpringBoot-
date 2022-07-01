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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

   @Autowired
   private LoginAttemptsUserServicesBOImpl userService;

   @Autowired
   protected AuthenticationManager authenticationManager;
   @Override
   public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                       AuthenticationException exception) throws IOException, ServletException {
      boolean validate = true;
      String email = request.getParameter("email");
      String password = request.getParameter("password");
      AppUser user = userService.getByEmail(email);



      if (user != null) {
         if (user.isEnabled() && user.isAccountNonLocked()) {
            if (user.getFailedAttempt() < LoginAttemptsUserServicesBOImpl.MAX_FAILED_ATTEMPTS - 1) {
               userService.increaseFailedAttempts(user);
            } else {
               user.setLocked(true);
               userService.lock(user);
               exception = new LockedException("Your account has been locked due to 3 failed attempts."
                       + " It will be unlocked after 10 seconds.");
            }
         } else if (!user.isAccountNonLocked()) {
            if (userService.unlockWhenTimeExpired(user)) {
               super.setAllowSessionCreation(true);

                  UsernamePasswordAuthenticationToken authToken =
                          new UsernamePasswordAuthenticationToken(user.getUsername(), password);
                  authToken.setDetails(new WebAuthenticationDetails(request));
                  Authentication authentication = authenticationManager.authenticate(authToken);
                  SecurityContextHolder.getContext().setAuthentication(authentication);
                  new CustomLoginSuccessHandler().onAuthenticationSuccess(request,response,authentication);


               validate =false;

               exception = new LockedException("Your account has been unlocked. Please try to login again.");
            }
         }
      }
    //  super.setDefaultFailureUrl("/login?error");a
      if (validate)  super.onAuthenticationFailure(request, response, exception);
   }

}