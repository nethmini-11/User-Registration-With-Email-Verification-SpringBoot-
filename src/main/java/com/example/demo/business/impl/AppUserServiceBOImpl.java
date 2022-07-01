/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */

package com.example.demo.business.impl;

import com.example.demo.DAO.AppUserRepositoryDAO;
import com.example.demo.business.AppUserServiceBO;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.ConfirmationToken;
import com.example.demo.enumpackage.AppUserRoleEnum;
import com.example.demo.enumpackage.AuthenticationType;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.demo.security.config.CommonConfig.USER_NOT_FOUND_MSG;


@Service
@AllArgsConstructor
@CrossOrigin
public class AppUserServiceBOImpl implements UserDetailsService, AppUserServiceBO {



    // App User DAO Class
    private final AppUserRepositoryDAO appUserRepositoryDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenServiceBOImpl confirmationTokenServiceBOImpl;


//    ////////////////////////////////////////////////////////////////////////////////


    @Override //Update Reset Password Method check User Is Enabled (Registration Confirmed By Email) Calling From Forgot Password Controller
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        AppUser user = appUserRepositoryDAO.findByUserEmail(email);
        if (user != null && user.getEnabled()) { // Check User In the Database and Is he Confirmed the Email Verification
            user.setResetPasswordToken(token);
            appUserRepositoryDAO.save(user);// Save Or Update User With Token
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    // Database Check Is there any User In the database from this token
    public AppUser getByResetPasswordToken(String token) {
        return appUserRepositoryDAO.findByResetPasswordToken(token);
    }

    @Override
    // Reset Password WITH NEW Password
    public void updatePassword(AppUser user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);// Set Rest Password Null after He Reset Password
        appUserRepositoryDAO.save(user);
    }

//    ///////////////////////////////////////////////////////////////////////////////


    @Override// Get User By Unique Email
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepositoryDAO.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepositoryDAO.findByEmail(appUser.getEmail()).isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);
        String aut = "Manual";
        AuthenticationType authManual = null;
        for(AuthenticationType auth : AuthenticationType.values()) {
            String aut2 = String.valueOf(auth);
            if(aut.equals(aut2)) {
                authManual = auth;
            }
        }
        appUser.setAuthType(authManual);

        appUserRepositoryDAO.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), appUser);

        confirmationTokenServiceBOImpl.saveConfirmationToken(confirmationToken);

//        TODO: SEND EMAIL

        return token;
    }



         public int enableAppUser(String email) {
        return appUserRepositoryDAO.enableAppUser(email);
    }
}
