package com.inventory.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.model.User;
import com.inventory.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    // REGISTER
    @PostMapping("/register")
    public void register(@RequestBody User user) {
        service.register(user);
    }

    // LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> data) {

        User user = service.login(
                data.get("username"), // email from frontend
                data.get("password"),
                data.get("role")
        );

        if (user == null) {
            throw new RuntimeException("Invalid credentials");
        }

        return Map.of(
                "username", user.getEmail(),
                "role", user.getRole(),
                "token", "dummy-token"
        );
    }

    // SEND OTP
    @PostMapping("/send-otp")
    public void sendOtp(@RequestBody Map<String, String> data) {
        service.sendOtp(data.get("username"));
    }

    // RESET PASSWORD
    @PostMapping("/reset-password")
    public void resetPassword(@RequestBody Map<String, String> data) {
        boolean ok = service.resetPassword(
                data.get("username"),
                data.get("otp"),
                data.get("newPassword")
        );

        if (!ok) {
            throw new RuntimeException("Invalid OTP");
        }
    }
}
