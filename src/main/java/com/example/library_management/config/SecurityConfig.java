package com.example.library_management.config;

import com.example.library_management.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomUserDetailsService customUserDetailsService) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/login", "/signup", "/forgot-password", "/css/**", "/js/**", "/images/**", "/h2-console/**").permitAll()
				.requestMatchers("/dashboard", "/books-ui", "/add-book", "/profile", "/change-password").authenticated()
				.anyRequest().authenticated())
			.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/dashboard", true)
				.failureUrl("/login?error=true")
				.permitAll())
			.rememberMe(remember -> remember
				.key("library-management-remember-me")
				.rememberMeParameter("rememberMe")
				.tokenValiditySeconds(1209600))
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.permitAll())
			.userDetailsService(customUserDetailsService)
			.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()));
		return http.build();
	}
}