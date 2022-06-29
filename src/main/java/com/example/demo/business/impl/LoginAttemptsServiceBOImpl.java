/**
 * @author - Chamath_Wijayarathna
 * Date :6/17/2022
 */


package com.example.demo.business.impl;


import com.example.demo.business.LoginAttemptsServiceBO;
import com.example.demo.entity.AppUser;
import lombok.Getter;

@Getter
public class LoginAttemptsServiceBOImpl implements LoginAttemptsServiceBO {


    private final AppUser appUser;

    public LoginAttemptsServiceBOImpl(AppUser appUser) {
        this.appUser = appUser;
    }


    public boolean isAccountNonLocked() {
        return appUser.isAccountNonLocked();
    }
}
