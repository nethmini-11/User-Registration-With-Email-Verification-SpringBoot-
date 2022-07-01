/**
 * @author - Chamath_Wijayarathna
 * Date :6/14/2022
 */

package com.example.demo.DAO;

import com.example.demo.entity.AppUser;
import com.example.demo.enumpackage.AuthenticationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface AppUserRepositoryDAO extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE AppUser a " + "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email); // Enable App User


    @Query("SELECT c FROM AppUser c WHERE c.email = ?1") // Find App User Object by email (unique).
    AppUser findByUserEmail(String email);

    AppUser findByResetPasswordToken(String token);


    @Query("UPDATE AppUser u SET u.failedAttempt = ?1 WHERE u.email = ?2")
    @Modifying
    public void updateFailedAttempts(int failAttempts, String email);
    @Modifying
    @Query("UPDATE AppUser u SET u.authType = ?2 WHERE u.email = ?1")
    void updateAuthenticationType(String username, AuthenticationType authType1);
}
