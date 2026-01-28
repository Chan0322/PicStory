package com.Insta.Picstory.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
	// JWT 토큰 생성 및 검증 클래스
	private final Key key;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}
	
	// Access Token 30분 유효 생성
	public String createAccessToken(String userId) {
		return createToken(userId, 30*60*1000L);
	}
	
	// Refresh Token 1일 유효 생성
	public String createRefreshToken(String userId) {
		return createToken(userId, 1*24*60*60*1000L);
	}
	
	// 토큰 생성 함수
	private String createToken(String userId, long validityInMs) {
		Claims claims = Jwts.claims().setSubject(userId);
		Date now = new Date();
		Date validity = new Date(now.getTime() + validityInMs);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	// 토큰 검증
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		}catch (Exception e) {
			// 토큰 만료 or 변조
			return false;
		}
	}
	
	// 토큰으로 사용자 조회
	public String getUserId(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build()
				.parseClaimsJws(token).getBody().getSubject();
	}
}
