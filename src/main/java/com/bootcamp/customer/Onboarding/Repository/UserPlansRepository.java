package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.User;
import com.bootcamp.customer.Onboarding.model.UserPlans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserPlansRepository extends JpaRepository<UserPlans,Long> {

    UserPlans findByUser(User user);

    @Query("SELECT up.plan.id FROM UserPlans up WHERE up.user.id = :userId")
    Long findPlanIdByUserId(@Param("userId") Long userId);

}
