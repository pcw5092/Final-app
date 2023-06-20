package org.edupoll.model.dto.response;

public class ErrorResponse {
	String massage;
	Long timestamp;

	public ErrorResponse(String massage, Long timestamp) {
		super();
		this.massage = massage;
		this.timestamp = timestamp;
	}

	public String getMassage() {
		return massage;
	}

	public Long getTimestamp() {
		return timestamp;
	}

}
