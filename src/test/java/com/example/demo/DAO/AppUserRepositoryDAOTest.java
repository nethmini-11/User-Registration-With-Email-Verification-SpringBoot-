package com.example.demo.DAO;

import com.example.demo.entity.AppUser;
import com.example.demo.enumpackage.AppUserRoleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AppUserRepositoryDAOTest {
    @Autowired
    private AppUserRepositoryDAO testAppUserRepositoryDAO;

    @Test
    void findByEmail() {
        // Given
        String email = "gemzone0k@gmail.com";
        Long d = Long.valueOf(323);
        AppUser appUser = new AppUser(

                d,
                "Rivindu",
                "Wijayarathna",
                email,
                "1111",
                AppUserRoleEnum.ROLE_USER
        );
        System.out.println(appUser.getPassword());
     //   testAppUserRepositoryDAO.save(appUser);
                // When
        //Optional<AppUser> exists = testAppUserRepositoryDAO.findByEmail(email);
       // System.out.println(exists);

 // Then
       // assertThat(exists).isEqualTo(expect);
    }

    @Test
    void enableAppUser() {
    }

    @Test
    void findByUserEmail() {
    }

    @Test
    void findByResetPasswordToken() {
    }

    @Test
    void updateFailedAttempts() {
    }
}