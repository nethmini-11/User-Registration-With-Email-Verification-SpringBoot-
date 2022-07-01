/**
 * @author - Chamath_Wijayarathna
 * Date :6/17/2022
 */

package com.example.demo.business.impl;

import com.example.demo.DAO.AppUserRepositoryDAO;
import com.example.demo.entity.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

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

    public void updateAuthenticationType(AppUser appUser) {
        try {
          Optional<AppUser> user =  repo.findByEmail(appUser.getEmail());
            if(user.isEmpty()) repo.save(appUser);
            else repo.updateAuthenticationType(appUser.getEmail(),appUser.getAuthType());

        }catch (Exception e){
          e.printStackTrace();
        }
    }
}
