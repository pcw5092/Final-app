package org.edupoll.controller;

import java.util.Base64;

import org.edupoll.exception.AlreadyVerifiedException;
import org.edupoll.exception.ExistUserEmailException;
import org.edupoll.exception.InvalidPasswordException;
import org.edupoll.exception.NotExistUserException;
import org.edupoll.exception.VerifyCodeException;
import org.edupoll.model.dto.request.CreateUserRequest;
import org.edupoll.model.dto.request.ValidateUserRequest;
import org.edupoll.model.dto.request.VerifyCodeRequest;
import org.edupoll.model.dto.request.VerifyEmailRequest;
import org.edupoll.model.dto.response.ValidateUserResponse;
import org.edupoll.model.dto.response.VerifyEmailResponse;
import org.edupoll.service.MailService;
import org.edupoll.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {

	private final UserService userService;

	private final MailService mailService;

	@PostMapping("/join")
	public ResponseEntity<Void> joinUserHandle(@Valid CreateUserRequest request)
			throws ExistUserEmailException, VerifyCodeException {

		userService.joinUser(request);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/validate")
	public ResponseEntity<ValidateUserResponse> validateHandle(@Valid ValidateUserRequest req)
			throws NotExistUserException, InvalidPasswordException {

		userService.validateUser(req);
		
		// 암호화
		var response =
				new ValidateUserResponse(200, Base64.getEncoder().encodeToString(req.getEmail().getBytes()));
		
		//log.info("encoded = " + encoded);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/verify-email")
	public ResponseEntity<VerifyEmailResponse> verifyEmailHandle(@Valid VerifyEmailRequest req)
			throws AlreadyVerifiedException {

		mailService.sendVerifactionCode(req);
		
		var response = new VerifyEmailResponse(200, "이메일 인증코드가 정상 발급되어있습니다");

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PatchMapping("/verify-email")
	public ResponseEntity<Void> verifyCodeHandle(@Valid VerifyCodeRequest req) throws VerifyCodeException {

		userService.verfiySpecificCode(req);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
