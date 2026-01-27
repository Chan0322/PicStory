package com.Insta.Picstory.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Insta.Picstory.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsById(String id);
	boolean existsByNickname(String nickname);
	// id를 이용해 사용자 조회
	Optional<User> findById(String id);
}
