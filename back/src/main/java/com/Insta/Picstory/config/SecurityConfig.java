package com.Insta.Picstory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	// 패스워드 암호화
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // 테스트를 위해 CSRF 보호 비활성화
            .cors(cors -> {}) // 컨트롤러의 @CrossOrigin 설정 따름
            .authorizeHttpRequests(auth -> auth
            		// 회원가입 api는 인증 없이 통과
            	.requestMatchers("/api/auth/**", "/api/test").permitAll()
                .anyRequest().authenticated() // 그 외 요청은 인증 필요.
            );
        return http.build();
    }
}