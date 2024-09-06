package com.bootcamp.customer.Onboarding.controller;

import com.bootcamp.customer.Onboarding.Service.PlansService;
import com.bootcamp.customer.Onboarding.model.Plans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/plans")
    public class PlansController {

        @Autowired
        private PlansService plansService;

        @GetMapping
        public List<Plans> getAllPlans(){
            return plansService.getAllPlans();
        }

        @GetMapping("/{id}")
        public Plans getPlanById(@PathVariable Long id){
            return plansService.getPlanById(id);
        }
    }



