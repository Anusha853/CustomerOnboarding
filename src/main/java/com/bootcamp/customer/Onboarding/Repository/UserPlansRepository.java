package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPlansRepository extends JpaRepository<UserPlans,Long> {

    List<UserPlans> findByUser(User user);

}
