package org.edupoll.service;

import org.edupoll.model.dto.request.CreateUserRequest;
import org.edupoll.model.entity.User;
import org.edupoll.repository.UserRepoitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	UserRepoitory userRepoitory;
	
	// 회원가입 처리 서비스
	@Transactional
	@PostMapping("/create")
	public boolean ceateUser(CreateUserRequest req) {
		
		User user = new User();
		
		userRepoitory.save(user);
		
		return false;
	
	}
}
