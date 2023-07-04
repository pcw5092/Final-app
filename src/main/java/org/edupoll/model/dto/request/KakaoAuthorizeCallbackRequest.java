package org.edupoll.model.dto.request;

import lombok.Data;

@Data
public class KakaoAuthorizeCallbackRequest {
	private String code;
	private String error;
}
