/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */

package com.example.demo.business.impl;

import com.example.demo.DAO.ConfirmationTokenRepositoryDAO;
import com.example.demo.entity.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceBOImpl { // Confirm Generated token is match to Email token


    // Calling to DAO Repository to ConfirmationToken to access Entity
    private final ConfirmationTokenRepositoryDAO confirmationTokenRepositoryDAO;

    // Save token before send an email, to check whether token in match to email verification token
    public void saveConfirmationToken(ConfirmationToken token) {
        confirmationTokenRepositoryDAO.save(token);
    }

    // Find Token by id
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepositoryDAO.findByToken(token);
    }

    // Once confirmed update confirmed time
    public int setConfirmedAt(String token) {
        return confirmationTokenRepositoryDAO.updateConfirmedAt(token, LocalDateTime.now());
    }
}
