package com.urbanlegends;

import com.urbanlegends.user.User;
import com.urbanlegends.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UrbanlegensApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrbanlegensApplication.class, args);
	}

	@Bean
	CommandLineRunner createInitialUsers(UserService userService){
		return ( args) -> {
			User user = User.builder()
					.username("user1")
					.displayName("user1")
					.password("Password123")
					.build();
			userService.saveUser(user);
			};

	}

}
