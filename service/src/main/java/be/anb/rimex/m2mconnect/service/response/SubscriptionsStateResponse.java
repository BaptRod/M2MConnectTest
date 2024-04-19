package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionsStateResponse {
	
	
	
	@JsonProperty("activate")
	private int activate;
	@JsonProperty("desactivate")
	private int desactivate;
	
	@JsonProperty("pause")
	private int pause;
	
	@JsonProperty("total")
	private int total;
	
	
	public SubscriptionsStateResponse(){
	
	}
	
	public SubscriptionsStateResponse(int activate, int desactivate, int pause, int total) {
		this.activate = activate;
		this.desactivate = desactivate;
		this.pause = pause;
		this.total = total;
	}
	
	public int getActivate() {
		return activate;
	}
	
	public void setActivate(int activate) {
		this.activate = activate;
	}
	
	public int getDesactivate() {
		return desactivate;
	}
	
	public void setDesactivate(int desactivate) {
		this.desactivate = desactivate;
	}
	
	public int getPause() {
		return pause;
	}
	
	public void setPause(int pause) {
		this.pause = pause;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
}
