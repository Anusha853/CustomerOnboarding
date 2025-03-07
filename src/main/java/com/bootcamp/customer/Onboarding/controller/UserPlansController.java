package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.PlansService;
import com.bootcamp.customer.Onboarding.Service.UserPlansService;
import com.bootcamp.customer.Onboarding.Service.UserService;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.UserDetailsDTO;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user/user-plans")
public class UserPlansController {

    @Autowired
    private UserPlansService userPlansService;
    @Autowired
    private UserService userService;
    @Autowired
    private PlansController plansController;
    @Autowired
    private PlansService plansService;

    @PostMapping("/addPlans")
    public ResponseEntity<Void> addPlansToUser(@RequestParam Long userId, @RequestParam List<Long> plansIds){
        userPlansService.addPlansToUser(userId,plansIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPlanToUser(@RequestParam Long userId, @RequestParam Long planId) {
        try {
            boolean addedPlans = userPlansService.addPlanToUser(userId, planId);
            if (addedPlans) {
                return ResponseEntity.ok("Plan added successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Plan already associated with the user.");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
