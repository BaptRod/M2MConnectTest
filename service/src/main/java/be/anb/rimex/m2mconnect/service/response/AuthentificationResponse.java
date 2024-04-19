package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthentificationResponse {
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("code")
	private int code;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("token")
	private String token;
	
	
	
	
	public AuthentificationResponse() {
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getToken(){ return token;}
}
