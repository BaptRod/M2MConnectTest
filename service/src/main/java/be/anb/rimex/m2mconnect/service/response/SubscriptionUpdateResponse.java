package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionUpdateResponse {
	
	@JsonProperty("message")
	private String message;
	
	
	
	public SubscriptionUpdateResponse(){
	
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
