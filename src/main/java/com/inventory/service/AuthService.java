package com.inventory.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.inventory.model.User;
import com.inventory.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository repo;

    public AuthService(UserRepository repo) {
        this.repo = repo;
    }

    // REGISTER
    public void register(User user) {
        user.setRole(user.getRole().toLowerCase());
        repo.save(user);
    }

    // LOGIN
    public User login(String email, String password, String role) {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isEmpty()) return null;

        User user = opt.get();
        if (!user.getPassword().equals(password)) return null;
        if (!user.getRole().equals(role)) return null;

        return user;
    }

    // SEND OTP
    public void sendOtp(String email) {
        User user = repo.findByEmail(email).orElseThrow();
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        user.setOtp(otp);
        repo.save(user);

        System.out.println("OTP for " + email + " : " + otp);
    }

    // RESET PASSWORD
    public boolean resetPassword(String email, String otp, String newPass) {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isEmpty()) return false;

        User user = opt.get();
        if (!otp.equals(user.getOtp())) return false;

        user.setPassword(newPass);
        user.setOtp(null);
        repo.save(user);
        return true;
    }
}
