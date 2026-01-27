package com.Insta.Picstory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
	// 로그인 시 사용하기 위한 정보를 담는 용도.
	private String id;
	private String pw;
}
