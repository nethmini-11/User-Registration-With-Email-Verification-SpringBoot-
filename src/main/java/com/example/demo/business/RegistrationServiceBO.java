package com.example.demo.business;

import com.example.demo.DTO.RegistrationRequestDTO;

public interface RegistrationServiceBO {
    String register(RegistrationRequestDTO request);

    String confirmToken(String token, String name, String email);
}
