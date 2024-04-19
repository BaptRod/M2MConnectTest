package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccountingInvoice {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	public AccountingInvoice() {
	
	}
	
	public AccountingInvoice(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
