/**
 * @author - Chamath_Wijayarathna
 * Date :6/17/2022
 */


package com.example.demo.business.impl;

import com.example.demo.DAO.AppUserRepositoryDAO;
import com.example.demo.entity.AppUser;
import com.example.demo.enumpackage.AuthenticationType;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;

import static com.example.demo.security.config.CommonConfig.USER_NOT_FOUND_MSG;

@Service
@Transactional
@Slf4j
public class LoginAttemptsUserServicesBOImpl {

    public static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = 10000; // 24 hours
    @Autowired
    private AppUserRepositoryDAO repo;

    public void increaseFailedAttempts(AppUser user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        repo.updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    public void resetFailedAttempts(String email) {
        repo.updateFailedAttempts(0, email);
    }

    public void lock(AppUser user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        log.info("");

        repo.save(user);
    }

    public boolean unlockWhenTimeExpired(AppUser user) {
        long lockTimeInMillis = user.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            user.setLocked(false);
            user.setLockTime(null);
            user.setFailedAttempt(0);

            repo.save(user);

            return true;
        }

        return false;
    }

    public AppUser getByEmail(String email) {
        return repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public void updateAuthenticationType(String username, String authTypeName) {
        AuthenticationType authType1 = AuthenticationType.valueOf(authTypeName.toUpperCase());
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR " +username);
       // repo.save(username, authType1);
    }
//String firstName, String lastName, String email, String password, AppUserRoleEnum appUserRoleEnum
  /*  public void updateAuthenticationType(String givenName, String name, String username, String password, String oauth2ClientName, String role) {
        AuthenticationType authType1 = AuthenticationType.valueOf(oauth2ClientName.toUpperCase());
        System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR " +username);
        repo.save(givenName,name, username,role);
    }*/

    public void updateAuthenticationType(AppUser appUser) {
        repo.save(appUser);
    }
}
