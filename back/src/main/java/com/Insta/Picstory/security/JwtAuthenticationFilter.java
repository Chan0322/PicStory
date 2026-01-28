package com.Insta.Picstory.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	// 매 요청시마다 토큰의 유효성 검증
	
	private final JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		
		// 헤더의 토큰 조회
		String token = resolveToken(request);
		
		// 토큰 유효 검사
		if(token != null && jwtTokenProvider.validateToken(token)) {
			// 유효한 경우 사용자 객체 생성
			String userId = jwtTokenProvider.getUserId(token);
			// 시큐리티용 인증 토큰
			UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userId, null, null);
			// SecurityContext에 인증정보 저장 => 컨트롤러에서 로그인으로 인식
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		// 다음 필터로
		filterChain.doFilter(request, response);
	}
	
	private String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
}
