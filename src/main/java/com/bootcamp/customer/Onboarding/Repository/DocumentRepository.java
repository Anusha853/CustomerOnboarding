package com.bootcamp.customer.Onboarding.Repository;

import com.bootcamp.customer.Onboarding.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByUserId(Long userId);
}
