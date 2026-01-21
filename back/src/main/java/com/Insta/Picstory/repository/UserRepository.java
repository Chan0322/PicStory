package com.Insta.Picstory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Insta.Picstory.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	boolean existsById(String id);
	boolean existsByNickname(String nickname);
}
