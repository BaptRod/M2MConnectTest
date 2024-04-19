package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionErrorResponse {
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("code")
	private int code;
	
	public SubscriptionErrorResponse(){
	
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
}
