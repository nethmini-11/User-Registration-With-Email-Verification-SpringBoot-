/**
 * @author - Chamath_Wijayarathna
 * Date :6/16/2022
 */

package com.example.demo.controller;

import com.example.demo.DTO.RegistrationRequestDTO;
import com.example.demo.business.RegistrationServiceBO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
@Slf4j
public class RegistrationController { // Registration Controller

    private final RegistrationServiceBO registrationServiceBO; // Reference to RegistrationServiceBO Service

    @PostMapping  // Register User Json Object
    // {   "firstName":"Rivindu",  "lastName" :"Wijayarathna", "email" : "gemzone0@gmail.com", "password" : "1234" }
    public String register(@RequestBody RegistrationRequestDTO request) {
        return registrationServiceBO.register(request);
    }

    @GetMapping(path = "confirm")
    // Confirmation email link will take token, name and email
    public String confirm(@RequestParam("token") String token, @RequestParam("name") String name, @RequestParam("email") String email) {
        String confirm = null;
        try {
            confirm = registrationServiceBO.confirmToken(token, name, email);// Method to Confirm token is match to the token stored in the database
        } catch (IllegalStateException e) {
            log.info("Token Not Found");
        }
        return confirm;
    }

}// End Controller
