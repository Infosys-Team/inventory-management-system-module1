package com.inventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.model.Alert;
import com.inventory.service.AlertService;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin("*")
public class AlertController {

    private final AlertService service;

    public AlertController(AlertService service) {
        this.service = service;
    }

    // GET all alerts
    @GetMapping
    public List<Alert> getAlerts() {
        return service.getAllAlerts();
    }

    // MARK alert as read (Admin)
    @PutMapping("/read/{id}")
    public void markRead(@PathVariable Long id) {
        service.markRead(id);
    }
}
