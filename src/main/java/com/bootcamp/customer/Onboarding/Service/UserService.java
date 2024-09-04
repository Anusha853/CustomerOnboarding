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

    @Autowired
    private EmailService emailService;


    public User registerUser(String username, String password, String email, String phoneNumber, int customerType) {

        if (username == null || username.trim().isEmpty()) {
            throw new ValidationException("Username is required");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new ValidationException("Password is required");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email is required");
        }
        if (customerType < 1 || customerType >= 5) {
            throw new ValidationException("Customer type is required");
        }

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setCustomerType(customerType);

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            try{
                emailService.sendLoginCongfirmation(user.getEmail());
            }catch (Exception e){
                e.printStackTrace();
            }
            return user;
        }
        return null;
    }

    public boolean checkIfUSerExists(String username){
        User user = userRepository.findByUsername(username);
        return user != null;
    }
}
