package org.edupoll.service;

import java.util.Optional;

import org.edupoll.exception.ExistUserEmailException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.exception.NotExistUserException;
import org.edupoll.exception.VerifyCodeException;
import org.edupoll.model.dto.request.CreateUserRequest;
import org.edupoll.model.dto.request.ValidateUserRequest;
import org.edupoll.model.dto.request.VerifyCodeRequest;
import org.edupoll.model.entity.User;
import org.edupoll.model.entity.VerificationCode;
import org.edupoll.repository.UserRepository;
import org.edupoll.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	VerificationCodeRepository verificationCodeRepository;

	// 회원가입 처리 서비스
	@Transactional
	public void joinUser(CreateUserRequest dto) 
			throws ExistUserEmailException, VerifyCodeException {
//		User found = userRepository.findByEmail(dto.getEmail());
		if (userRepository.existsByEmail(dto.getEmail())) {
			throw new ExistUserEmailException();
		}
		
		// 인증절차를 거쳤는지 확인
		VerificationCode found = verificationCodeRepository
		        .findTop1ByEmailOrderByCreatedDesc(dto.getEmail())
		        .orElseThrow(() -> new VerifyCodeException());

		if (found.getState() == null || found.getState().equals("")) {
		    throw new VerifyCodeException();
		}

		// 회원정보 등록
		User one = new User();
		one.setEmail(dto.getEmail());
		one.setName(dto.getName());
		one.setPassword(dto.getPassword());
		userRepository.save(one);

	}

	// 로그인 처리 서비스
	@Transactional
	public void validateUser(ValidateUserRequest req) 
			throws NotExistUserException, InvalidPasswordException {
		
		User found = userRepository.findByEmail(req.getEmail()).orElseThrow(() 
				-> new NotExistUserException());
//		if(found == null) {
//			throw new NotExistUserException();
//		}

		boolean isSame = found.getPassword().equals(req.getPassword());
		if (!isSame) {
			throw new InvalidPasswordException();
		}

	}
	
	
	// 인증코드 처리 서비스
	@Transactional
	public void verfiySpecificCode(@Valid VerifyCodeRequest req) throws VerifyCodeException {
		Optional<VerificationCode> result = verificationCodeRepository
				.findTop1ByEmailOrderByCreatedDesc(req.getEmail());

		VerificationCode found = result.orElseThrow(() -> new VerifyCodeException());
		long elapsed = System.currentTimeMillis() - found.getCreated().getTime();

		if (elapsed > 1000 * 60 * 10) {
			throw new VerifyCodeException();
		}

		if (found.getCode().equals(req.getCode())) {
			throw new VerifyCodeException();
		}
		found.setState("");

		verificationCodeRepository.save(found);
	}

}
