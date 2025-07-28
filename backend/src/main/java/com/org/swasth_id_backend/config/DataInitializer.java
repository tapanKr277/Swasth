package com.org.swasth_id_backend.config;

import com.org.swasth_id_backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initialize(UserService userService) {
        return args -> userService.initializeRolesAndAdmin();
    }
}
