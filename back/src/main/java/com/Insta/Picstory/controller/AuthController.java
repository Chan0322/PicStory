package com.Insta.Picstory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Insta.Picstory.dto.UserSignupDTO;
import com.Insta.Picstory.entity.User;
import com.Insta.Picstory.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@PostMapping("/signup")
	public String signup(@RequestBody UserSignupDTO dto) {
		// 아이디, 닉네임 중복 확인
		if(userRepository.existsById(dto.getId())) return "이미 사용중인 아이디 입니다.";
		if(userRepository.existsByNickname(dto.getNickname())) return "이미 사용중인 이메일 입니다.";
		
		// DTO에서 엔티티로
		User user = User.builder()
				.id(dto.getId())
				.pw(passwordEncoder.encode(dto.getPw()))
				.userName(dto.getUserName())
				.nickname(dto.getNickname())
				.build();
		
		// db 저장
		userRepository.save(user);
		return "회원가입이 완료되었습니다. 환영합니다!";
	}
}
