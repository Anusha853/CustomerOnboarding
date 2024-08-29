package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.CustomerTypeRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.exceptions.ValidationException;
import com.bootcamp.customer.Onboarding.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerTypeRepository customerTypeRepository;


    public User registerUser(String username, String password, String email, String customerType){

        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (customerType == null || customerType.trim().isEmpty()) {
            throw new ValidationException("Customer type is required");
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setCustomerType(customerType);

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password,user.getPasswordHash())) {
            return user;
        }
        return null;
    }


}
