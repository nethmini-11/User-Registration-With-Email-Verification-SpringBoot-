package com.example.demo.business.impl;

import com.example.demo.DAO.AppUserRepositoryDAO;
import com.example.demo.entity.AppUser;
import com.example.demo.enumpackage.AppUserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
class AppUserServiceBOImplTest {

    private static final String firstName = "kamal";
    private static final String lastName = "pk";
    private static final String email = "gemzone0@gmail.com";
    private static final Long id = 23L;
    @InjectMocks
    private AppUserServiceBOImpl appUserServiceBO;
    @Mock
    private AppUserRepositoryDAO appUserRepositoryDAO;

    @Test
    public void findById() {
        //Arange
        AppUser appUser = new AppUser(id, firstName, lastName, email, "1111", AppUserRoleEnum.ROLE_USER);

        Mockito.when(appUserRepositoryDAO.findByEmail(email)).thenReturn(Optional.of(appUser));
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAgfgfgfgfggggggggggAAAAAA ");
        UserDetails e1ByService = appUserServiceBO.loadUserByUsername(email);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA " + e1ByService.getUsername());
        assertEquals(appUser.getUsername(), e1ByService.getUsername());

    }


    @Test
    @Order(2)
    void loadUserByUsername() {
//        // Set up the mock repo
//        Long aLong = 323L;
//        String employeeEmail = "gemzone0@gmail.com";
//        AppUser e1ForMock = new AppUser(aLong,"Wijayarathna", "Rivindu",employeeEmail, "1111", AppUserRoleEnum.ROLE_USER);
//        doReturn(Optional.of(e1ForMock)).when(appUserRepositoryDAO).findByEmail(employeeEmail);
//        // Make the service call
//        UserDetails e1ByService = appUserServiceBO.loadUserByUsername(employeeEmail);
//        // Assert the response
//        assertNotNull(e1ByService,"Employee with employeeId : "+employeeEmail+" not found");
//        assertEquals(employeeEmail,e1ByService.getUsername());
//        assertEquals(e1ForMock.getUsername(), e1ByService.getUsername());
//        assertEquals(e1ForMock.getPassword(), e1ByService.getPassword());

    }

    @Test
    void updateResetPasswordToken() {
    }

    @Test
    void getByResetPasswordToken() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void signUpUser() {
        AppUser appUser = new AppUser(
                1L,
                "Rivindu",
                "Wijayarathna",
                "gemzone10@gmail.com",
                "11111", AppUserRoleEnum.ROLE_USER);
        when(appUserRepositoryDAO.save(appUser)).thenReturn(appUser);
        assertEquals(appUser, appUserServiceBO.signUpUser(appUser));
    }

    @BeforeEach
    void beforeAll() {

        ReflectionTestUtils.setField(appUserServiceBO, "bCryptPasswordEncoder", new BCryptPasswordEncoder());
        //ReflectionTestUtils.setField(appUserServiceBO, "confirmationTokenServiceBOImpl", new ConfirmationTokenServiceBOImpl());
    }

    @Test
    void enableAppUser() {
    }
}