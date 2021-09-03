package com.swlab.dashboard;

import com.swlab.dashboard.config.properties.GitlabProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties(GitlabProperties.class)
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner runner(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
//		return (args -> {
//			User user = User.builder().email("qwer@qwer.qwer").password(passwordEncoder.encode("1234")).name("jo").phoneNo("01028788714").build();
//			userRepository.save(user);
//			userRoleRepository.save(UserRole.builder().user(user).roleType(UserRole.RoleType.USER).build());
//		});
//	}
}
