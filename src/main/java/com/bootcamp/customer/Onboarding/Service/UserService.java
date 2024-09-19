package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.*;
import com.bootcamp.customer.Onboarding.controller.DocumentController;
import com.bootcamp.customer.Onboarding.exceptions.ValidationException;
import com.bootcamp.customer.Onboarding.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public boolean updatePassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        try{
            user.setPasswordHash(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    public UserDetailsDTO authenticate(String username, String password) {

        User user = userRepository.findByUsername(username);
        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            return null;
        }
        // Fetch all plans associated with the user
        List< Plans > plans = userPlansRepository.findPlansByUserId(user.getUserId());
        List < PlanDTO > plansList = new ArrayList< >();
        for (Plans plan: plans) {
            if (plan != null) {
                plansList.add(new PlanDTO(
                        plan.getPlan_name(),
                        plan.getPlan_description(),
                        plan.getPrice(),
                        plan.getValidity_days()
                ));
            }
        }
        boolean doc_verified = documentRepository.isDocumentVerified(user.getUserId());
        return new UserDetailsDTO(
                user.getUsername(),
                user.getPhoneNumber(),
                user.getCustomerType(),
                plansList,
                doc_verified,
                user.getEmail()
        );
    }
// Admin methods
public List<AdminDto> getAllUsers() {
    return userRepository.findAll().stream().map(this::convertToAdminDto).collect(Collectors.toList());
}

    public List<AdminDto> getUsersByType(int customerType) {
        return userRepository.findByCustomerType(customerType).stream()
                .map(this::convertToAdminDto)
                .collect(Collectors.toList());
    }
    public List<AdminDto> getUsersByDocumentStatus(boolean isVerified) {
        // Find users with the specified document status
        List<User> usersWithStatus = documentRepository.findByStatus(isVerified).stream()
                .map(Document::getUser)
                .collect(Collectors.toList());

        return usersWithStatus.stream()
                .map(this::convertToAdminDto)
                .collect(Collectors.toList());
    }

    private AdminDto convertToAdminDto(User user) {
        Optional<Document> document = documentRepository.findById(user.getUserId());
        String documentStatus = document.isPresent() && document.get().isStatus() ? "Verified" : "Not Verified";

        List<Long> planIds = new ArrayList<>();
        List<String> planNames = new ArrayList<>();

        user.getUserPlans().forEach(up -> {
            planIds.add(up.getPlan().getPlan_id());
            planNames.add(up.getPlan().getPlan_name());
        });

        String customerTypeName = user.getCustomerTypeEntity() != null ? user.getCustomerTypeEntity().getDescription() : "Unknown";

        return new AdminDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                customerTypeName,
                documentStatus,
                planIds,
                planNames
        );
    }
    public boolean togglePlanActivation(Long userId, Long planId) {
        Optional<UserPlans> userPlanOptional = userPlansRepository.findByUser_UserIdAndPlan_PlanId(userId, planId);
        if (userPlanOptional.isPresent()) {
            UserPlans userPlan = userPlanOptional.get();
            userPlan.setActivated(!userPlan.isActivated());
            userPlansRepository.save(userPlan);
            return true;
        }
        return false;
    }

}
