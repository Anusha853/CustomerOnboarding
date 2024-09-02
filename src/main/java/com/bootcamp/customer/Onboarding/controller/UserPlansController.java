package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.UserPlansService;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user/user-plans")
public class UserPlansController {

    @Autowired
    private UserPlansService userPlansService;

    @PostMapping("/add")
    public ResponseEntity<Void> addPlansToUser(@RequestParam Long userId, @RequestParam List<Long> plansIds){
        userPlansService.addPlansToUser(userId,plansIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserPlans>>
    getUserPlans(@PathVariable Long userId){
        List<UserPlans> userPlans = userPlansService.getUserPlans(userId);
        return ResponseEntity.ok(userPlans);
    }
}
