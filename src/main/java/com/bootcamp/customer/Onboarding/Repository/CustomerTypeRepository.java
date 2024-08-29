package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.CustomerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeRepository extends JpaRepository<CustomerType,Long> {
}
