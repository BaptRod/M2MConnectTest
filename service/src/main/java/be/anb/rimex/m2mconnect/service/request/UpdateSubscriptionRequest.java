package be.anb.rimex.m2mconnect.service.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateSubscriptionRequest {
	
	@JsonProperty("iccid")
	private String iccid;
	
	@JsonProperty("update")
	private String update;
	
	
	
	@JsonProperty("value")
	private String value;
	
	
	public UpdateSubscriptionRequest(String iccid, String update, String value) {
		this.iccid = iccid;
		this.update = update;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public UpdateSubscriptionRequest(){
	
	}
	
	public String getIccid() {
		return iccid;
	}
	
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	public String getUpdate() {
		return update;
	}
	
	public void setUpdate(String update) {
		this.update = update;
	}
}
