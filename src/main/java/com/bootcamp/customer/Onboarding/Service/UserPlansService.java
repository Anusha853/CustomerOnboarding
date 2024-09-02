package com.bootcamp.customer.Onboarding.Service;

import com.bootcamp.customer.Onboarding.Repository.PlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserPlansRepository;
import com.bootcamp.customer.Onboarding.Repository.UserRepository;
import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserPlansService {

    @Autowired
    private UserPlansRepository userPlansRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlansRepository plansRepository;

    public void addPlansToUser(Long userId, List<Long> planIds) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User Not Found")
        );

        for (Long plandId : planIds) {
            Plans plan = plansRepository.findById(plandId).orElseThrow(
                    () -> new RuntimeException("Plan not found")
            );

            UserPlans userPlans = new UserPlans();
            userPlans.setUser(user);
            userPlans.setPlan(plan);
            userPlans.setActivation_date(new Date());
            userPlans.setExpiration_date(new Date(System.currentTimeMillis()+(plan.getValidity_days()*24*60*60*1000L)));
            userPlansRepository.save(userPlans);
        }
    }
    public List<UserPlans> getUserPlans(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new RuntimeException("User Not Found")
        );
        return userPlansRepository.findByUser(user);
    }
}