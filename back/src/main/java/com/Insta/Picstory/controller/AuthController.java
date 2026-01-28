package com.Insta.Picstory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Insta.Picstory.dto.UserLoginDto;
import com.Insta.Picstory.dto.UserSignupDTO;
import com.Insta.Picstory.entity.User;
import com.Insta.Picstory.repository.UserRepository;
import com.Insta.Picstory.security.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
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
	
	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserLoginDto dto, HttpServletResponse response) {
		// id를 이용해 사용자 조히
		User user = userRepository.findById(dto.getId()).orElse(null);
		
		if(user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("가입 정보가 존재하지 않습니다. 회원가입 후 이용해주세요.");
		}
		
		// 비밀번호 확인
		if(!passwordEncoder.matches(dto.getPw(), user.getPw())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호가 일치하지 않습니다.");
		}
		
		// 토큰 생성
		String accessToken = jwtTokenProvider.createAccessToken(user.getId());
		String refreshToken = jwtTokenProvider.createRefreshToken(user.getId());
		
		// 쿠키에 refresh 토큰 담기
		ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(false)
				.path("/")
				.maxAge(1*24*60*60)
				.build();
		response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
		
		// body에 access 토큰 담기
		Map<String, String> result = new HashMap<>();
		result.put("accessToken", accessToken);
		result.put("nickname", user.getNickname());
		
		return ResponseEntity.ok(result);
	}
	
	// 새로고침
	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest request){
		//리프레쉬 토큰 쿠키에서 조회
		Cookie[] cookies = request.getCookies();
		String refreshToken = null;
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("refreshToken")) {
					refreshToken = cookie.getValue();
				}
			}
		}
		// 토큰 검사
		if(refreshToken != null && jwtTokenProvider.validateToken(refreshToken)) {
			String userId = jwtTokenProvider.getUserId(refreshToken);
			// 새 액세스 토큰 생성
			String newAccessToken = jwtTokenProvider.createAccessToken(userId);
			
			Map<String, String> result = new HashMap<>();
			result.put("accessToken", newAccessToken);
			return ResponseEntity.ok(result);
		}
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("refresh 토큰 만료");
	}
	
	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response){
		// 쿠키를 만료시켜서 삭제
	    ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
	            .httpOnly(true)
	            .secure(false)
	            .path("/")
	            .maxAge(0) // 즉시 삭제
	            .build();
	    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
	    return ResponseEntity.ok("로그아웃 되었습니다.");
	}
}
