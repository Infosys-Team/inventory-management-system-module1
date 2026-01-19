package com.inventory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.inventory.model.Alert;
import com.inventory.repository.AlertRepository;

@Service
public class AlertService {

    private final AlertRepository repo;

    public AlertService(AlertRepository repo) {
        this.repo = repo;
    }

    public void createAlert(String type, String message) {
        Alert alert = new Alert();
        alert.setType(type);
        alert.setMessage(message);
        alert.setStatus("Unread");
        repo.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return repo.findAll();
    }

    public void markRead(Long id) {
        Alert alert = repo.findById(id).orElseThrow();
        alert.setStatus("Read");
        repo.save(alert);
    }
}
