package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionPackPriceResponse {
	
	@JsonProperty("pack")
	private String pack;
	
	@JsonProperty("price")
	private float price;
	
	public SubscriptionPackPriceResponse() {
	}
	
	public SubscriptionPackPriceResponse(String pack, float price) {
		this.pack = pack;
		this.price = price;
	}
	
	public String getPack() {
		return pack;
	}
	
	public void setPack(String pack) {
		this.pack = pack;
	}
	
	public float getPrice() {
		return price;
	}
	
	public void setPrice(float price) {
		this.price = price;
	}
}
