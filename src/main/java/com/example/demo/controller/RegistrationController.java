/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */

package com.example.demo.controller;

import com.example.demo.DTO.RegistrationRequestDTO;
import com.example.demo.business.RegistrationServiceBO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController { // Registration Controller

    private final RegistrationServiceBO registrationServiceBO; // Reference to RegistrationServiceBOImpl

    @PostMapping
    public String register(@RequestBody RegistrationRequestDTO request) {
        return registrationServiceBO.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token, @RequestParam("name") String name, @RequestParam("email") String email) {
        return registrationServiceBO.confirmToken(token, name, email);
    }

}
