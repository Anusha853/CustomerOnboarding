package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.model.Plans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlansService {

    @Autowired
    private PlansRepository plansRepository;

    public List<Plans> getAllPlans(){
        return plansRepository.findAll();
    }
    public Plans getPlanById(Long id){
        return plansRepository.findById(id).orElse(null);
    }

}
