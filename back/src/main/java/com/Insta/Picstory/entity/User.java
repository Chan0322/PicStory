package com.Insta.Picstory.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="USER_TB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
	@SequenceGenerator(name = "USER_SEQ_GEN", sequenceName = "USER_SEQ", allocationSize = 1)
	@Column(name = "USER_NO")
	private Long userNo;
	
	@Column(name = "ID", unique = true, nullable = false)
	private String id;
	
	@Column(name = "PW", nullable = false)
	private String pw;
	
	@Column(name = "USER_NAME", nullable = false)
	private String userName;
	
	@Column(name = "NICKNAME", unique = true, nullable = false)
	private String nickname;
	
	@Builder.Default
	@Column(name = "IS_PRIVATE")
	private String isPrivate = "N";
	
	@Column(name = "CREATE_AT", updatable = false)
	private LocalDateTime createAt = LocalDateTime.now();
	
	@PrePersist
	public void onPrePersist() {
		if(this.isPrivate == null) this.isPrivate="N";
		this.createAt = LocalDateTime.now();
	}
}
