package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
