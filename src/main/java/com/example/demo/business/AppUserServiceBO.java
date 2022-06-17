package com.example.demo.business;

import com.example.demo.entity.AppUser;

public interface AppUserServiceBO {
    void updateResetPasswordToken(String token, String email);

    AppUser getByResetPasswordToken(String token);

    void updatePassword(AppUser user, String password);
}
