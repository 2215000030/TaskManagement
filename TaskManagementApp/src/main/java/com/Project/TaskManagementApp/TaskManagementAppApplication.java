package com.Project.TaskManagementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@EnableScheduling
@SpringBootApplication
public class TaskManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementAppApplication.class, args);
	}


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
		org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();

		configuration.setAllowedOrigins(java.util.List.of("http://localhost:5173")); // frontend
		configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(java.util.List.of("*"));
		configuration.setAllowCredentials(true);

		org.springframework.web.cors.UrlBasedCorsConfigurationSource source =
				new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
