package com.inventory.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
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

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            service.register(user);
            return ResponseEntity.ok("Registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {

        User user = service.login(
                data.get("email"),
                data.get("password"),
                data.get("role")
        );

        if (user == null) {
            return ResponseEntity.status(401)
                    .body("Invalid email / password / role");
        }

        return ResponseEntity.ok(
                Map.of(
                        "username", user.getEmail(),
                        "role", user.getRole(),
                        "token", "dummy-token"
                )
        );
    }

    // ================= SEND OTP =================
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody Map<String, String> data) {
        service.sendOtp(data.get("email"));
        return ResponseEntity.ok("OTP sent");
    }

    // ================= RESET PASSWORD =================
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody Map<String, String> data) {

        boolean ok = service.resetPassword(
                data.get("email"),
                data.get("otp"),
                data.get("newPassword")
        );

        if (!ok) {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }

        return ResponseEntity.ok("Password reset successful");
    }
}
