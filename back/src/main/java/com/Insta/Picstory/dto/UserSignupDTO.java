package com.Insta.Picstory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupDTO {
	private String id;	// 이메일 or 전화번호
	private String pw;
	private String userName;
	private String nickname;
}
