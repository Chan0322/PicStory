package com.Insta.Picstory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Insta.Picstory.entity.User;
import com.Insta.Picstory.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser() {
		// Jwt 필터에서 저장한 인증 정보 조회
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = (String) authentication.getPrincipal();
		
		// 사용자 DB에서 조회
		User user = userRepository.findById(userId).orElse(null);
		
		if(user == null) {
			return ResponseEntity.status(401).body("인증되지 않은 사용자입니다.");
		}
		
		// 사용자의 민감 정보를 제외하고 리턴
		Map<String, Object> userInfo = new HashMap<>();
		userInfo.put("id", user.getId());
		userInfo.put("userName", user.getUserName());
		userInfo.put("nickname", user.getNickname());
		userInfo.put("userNo", user.getUserNo());
		
		return ResponseEntity.ok(userInfo);
	}
}
