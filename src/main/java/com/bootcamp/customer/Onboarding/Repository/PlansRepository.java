package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.Plans;
import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlansRepository extends JpaRepository<Plans,Long> {

    //Plans findByPlanId(Long plan_id);
    Plans findByPlanId(Long plan_id);


}
