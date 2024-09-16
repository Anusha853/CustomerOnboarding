package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.*;
import com.bootcamp.customer.Onboarding.controller.DocumentController;
import com.bootcamp.customer.Onboarding.exceptions.ValidationException;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserDetailsDTO;
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
    private UserPlansRepository userPlansRepository;

    @Autowired
    private PlansRepository plansRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private OtpService otpService;



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
        try{
            emailService.sendEmail(user.getEmail(),user.getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }

        return userRepository.save(user);
    }

    public User loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    public boolean checkIfUSerExists(String email){
        User user = userRepository.findByEmail(email);
       return user != null;
    }

    public boolean sendOtp(String username) {
        User user = userRepository.findByUsername(username);
        try{
            otpService.generateAndSendOtp(user.getEmail(),username);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            otpService.generateAndSendOtp(user.getEmail(),username);
            return true;
        }
        return false;
    }

    public UserDetailsDTO authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        Long planid=userPlansRepository.findPlanIdByUserId(user.getUserId());

        Plans plans = plansRepository.findByPlanId(planid);
        boolean doc_verified=documentRepository.isDocumentVerified(user.getUserId());
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            if(plans!=null){
                return new UserDetailsDTO(
                        user.getUsername(),
                        user.getUsername(),
                        user.getPhoneNumber(),
                        user.getCustomerType(),
                        plans.getPlan_name(),
                        plans.getPlan_description(),
                        plans.getPrice(),
                        plans.getValidity_days(),
                        doc_verified
                );}
            else{
                return new UserDetailsDTO(
                        user.getUsername(),
                        user.getUsername(),
                        user.getPhoneNumber(),
                        user.getCustomerType(),
                        doc_verified
                );

            }
        }
        return null;
    }



}
