package com.example.realestate;

import com.example.realestate.models.Admin;
import com.example.realestate.models.User;
import com.example.realestate.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RealestateApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealestateApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(UserRepository userRepository) {
		return args -> {

			if (!userRepository.existsByEmail("superadmin@gmail.com")) {

				Admin superAdmin = new Admin();
				superAdmin.setFullName("Main Administrator");
				superAdmin.setEmail("superadmin@gmail.com");
				superAdmin.setPassword("admin123");
				superAdmin.setRole(User.Role.ADMIN);
				superAdmin.setStatus(User.Status.ACTIVE);


				superAdmin.setUsername("super_admin");
				superAdmin.setAdminLevel(Admin.AdminLevel.SUPER_ADMIN);

				userRepository.save(superAdmin);
				System.out.println("✅ Super Admin created successfully!");
			}
		};
	}
}