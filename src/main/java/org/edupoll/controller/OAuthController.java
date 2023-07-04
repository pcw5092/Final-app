package org.edupoll.controller;

import org.edupoll.model.dto.KakaoAccessTokenWrapper;
import org.edupoll.model.dto.KakaoAccount;
import org.edupoll.model.dto.request.KakaoAuthorizeCallbackRequest;
import org.edupoll.model.dto.request.ValidateKakaoRequest;
import org.edupoll.model.dto.response.OAuthSignResponse;
import org.edupoll.model.dto.response.ValidateUserResponse;
import org.edupoll.service.JWTService;
import org.edupoll.service.KakaoAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@CrossOrigin // 외부에서 접속 할 수 있다. (교차자원)
public class OAuthController {

	private final KakaoAPIService kakaoAPIService;
	private final JWTService jwtService;

	@Value("${kakao.restapi.key}")
	String kakaoRestApiKey;
	@Value("${kakao.redirect.url}")
	String kakaoRedirectUrl;

	

	// 카카오 인증 요청시 인증해야될 주소 알려주는 API
	@GetMapping("/kakao")
	public ResponseEntity<OAuthSignResponse> oAuthKakaoHandle() {
		// 카카오인증을 할 수 있는 주소를 보내주면 됨
		var response = new OAuthSignResponse(200,
				"https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" + kakaoRestApiKey
						+ "&redirect_uri=" + kakaoRedirectUrl);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/*
	 * // 카카오 로그인 후 코드를 받는 곳 // (프론트에서 처리해야한다.) => 이 코드값을 백엔드로 전달해주는 방식으로 바뀌게 된다.
	 * 
	 * @GetMapping("/kakao/callback") public ResponseEntity<OAuthSignResponse>
	 * oauthKakaoCallbackHandle(KakaoAuthorizeCallbackRequest req) {
	 * 
	 * log.info("code = {}", req.getCode());
	 * 
	 * return new ResponseEntity<>(HttpStatus.OK); }
	 */

	// (완) 카카오 인증코드로 사용자 정보 얻어내는 API
	@PostMapping("/kakao")
	public ResponseEntity<ValidateUserResponse> oauthKakaoPostHandle(ValidateKakaoRequest req)
			throws JsonMappingException, JsonProcessingException {

		KakaoAccessTokenWrapper wrapper = kakaoAPIService.getAccessToken(req.getCode());
		KakaoAccount account = kakaoAPIService.getUserInfo(wrapper.getAccessToken());
		
		log.info("kako = {}", account.toString());
		
		String token = jwtService.createToken(account.getEmail());
		ValidateUserResponse response = new ValidateUserResponse(200, token, account.getEmail());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
/*
 * Spring framework 에서 REST API 를 호출을 도와주기 위해서 
 *  - RestTemplate - 동기 (blocking IO)
 *  - WebClient - 비동기 (Non-blocking IO)
 */
