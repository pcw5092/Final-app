package org.edupoll.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class KakaoAccessTokenWrapper {
	
	@JsonProperty("token_type")
	private String tokenType;
	@JsonProperty("access_token")
	private String accessToken;
	@JsonProperty("expries_in")
	private int expiresIn;
	@JsonProperty("refresh_token")
	private String refreshToken;
	@JsonProperty("refresh_token_expires_in")
	private int refreshTokenExpiresIn;
}
