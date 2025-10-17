package com.spring.bluestonewebhook.repo;


import com.spring.bluestonewebhook.entity.WebhookEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebhookEventRepository extends JpaRepository<WebhookEvent, Long> {
    // You can add custom query methods here if needed later
}