package be.anb.rimex.m2mconnect.service.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthentificationRequest {
	
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	
	public AuthentificationRequest(String email, String password){
		this.email = email;
		this.password = password;
	}
}
