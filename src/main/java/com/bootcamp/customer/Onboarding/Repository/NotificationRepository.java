package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.Notification;
import com.bootcamp.customer.Onboarding.model.NotificationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, NotificationId> {
}
