//package com.tarkhan.blogapp.runner;
//
//import com.tarkhan.blogapp.entity.Role;
//import com.tarkhan.blogapp.entity.User;
//import com.tarkhan.blogapp.repository.UserRepository;
//import com.tarkhan.blogapp.service.auth.JwtService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//
//@Configuration
//@RequiredArgsConstructor
//public class DataInitializer {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtService jwtService;
//
//    @Bean
//    public CommandLineRunner run() {
//        return args -> {
//            if (!userRepository.existsByUsername("admin")) {
//                User admin = new User();
//                admin.setUsername("admin");
//                admin.setEmail("admin@gmail.com");
//                admin.setFirstName("Admin");
//                admin.setLastName("Admin");
//                admin.setCreatedAt(LocalDateTime.now());
//                admin.setUpdatedAt(LocalDateTime.now());
//                admin.setPassword(passwordEncoder.encode("admin"));
//                admin.setRole(Role.ADMIN);
//
//                userRepository.save(admin);
//
//                String token = jwtService.generateToken(admin);
//                System.out.println("Admin token: " + token);
//            }
//        };
//    }
//}
