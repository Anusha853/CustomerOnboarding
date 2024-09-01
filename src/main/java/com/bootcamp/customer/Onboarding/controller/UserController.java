package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser( @RequestParam String username, @RequestParam String password, @RequestParam String email, @RequestParam String phone, @RequestParam int customerType ){

        try {

            if(!userService.checkIfUSerExists(username)) {
                userService.registerUser(username, password, email, phone, customerType);
                return new ResponseEntity<>("Registration Successfully", HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>("User Already exists", HttpStatus.CONFLICT);
            }

        }catch (Exception e) {
            return new ResponseEntity<>("Error registering user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        User user = userService.loginUser(username, password);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

}
