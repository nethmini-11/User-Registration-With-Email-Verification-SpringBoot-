/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */

package com.example.demo.business.impl;

import com.example.demo.DAO.AppUserRepositoryDAO;
import com.example.demo.business.AppUserServiceBO;
import com.example.demo.entity.AppUser;
import com.example.demo.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
@CrossOrigin
public class AppUserServiceBOImpl implements UserDetailsService, AppUserServiceBO {

    private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

    private final AppUserRepositoryDAO appUserRepositoryDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenServiceBOImpl confirmationTokenServiceBOImpl;


//    ////////////////////////////////////////////////////////////////////////////////


    @Override
    public void updateResetPasswordToken(String token, String email) throws UsernameNotFoundException {
        AppUser user = appUserRepositoryDAO.findByUserEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            appUserRepositoryDAO.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any customer with the email " + email);
        }
    }

    @Override
    public AppUser getByResetPasswordToken(String token) {
        return appUserRepositoryDAO.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(AppUser user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        appUserRepositoryDAO.save(user);
    }

//    ///////////////////////////////////////////////////////////////////////////////


    @Override
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
