package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.PlanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanTypeRepository extends JpaRepository<PlanType,Long> {
}
