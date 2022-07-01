package com.example.demo.business.impl;

import com.example.demo.DAO.AppUserRepositoryDAO;
import com.example.demo.entity.AppUser;
import com.example.demo.enumpackage.AppUserRoleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
class RegistrationServiceBOImplTest {
    @Autowired
    private AppUserServiceBOImpl registrationServiceBO;
    @MockBean
    private AppUserRepositoryDAO appUserRepositoryDAO;

    @Test
    void register() {
        when(appUserRepositoryDAO.findAll()).thenReturn((List<AppUser>) Stream.of(new AppUser("Rivindu",
                " Wijayarathna","gemzone0@gmail.com", "11111", AppUserRoleEnum.ROLE_USER),new AppUser("Chamath",
                " Wijayarathna","gemzone1@gmail.com", "11111", AppUserRoleEnum.ROLE_USER)).collect(Collectors.toList()));
       // Assertions.assertEquals(2,registrationServiceBO.register());
    }

    @Test
    void confirmToken() {
    }
}