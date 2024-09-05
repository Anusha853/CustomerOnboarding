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

    @PostMapping("/add")
    public ResponseEntity<Void> addPlansToUser(@RequestParam Long userId, @RequestParam List<Long> plansIds){
        userPlansService.addPlansToUser(userId,plansIds);
        return ResponseEntity.ok().build();
    }

    /*
    @GetMapping("/profile")
    public ResponseEntity<Plans>
    getUserPlans(@RequestBody Map<String, String> loginRequest){
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Long userid = userService.getUserId(username, password);
        Long planid=plansService.getUserPlanId(userid);

        Plans userPlans = plansService.getPlanById(planid);

            return ResponseEntity.ok(userPlans);

    }*/
}
