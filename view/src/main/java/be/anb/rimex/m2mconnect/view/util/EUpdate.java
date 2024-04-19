package be.anb.rimex.m2mconnect.view.util;

public enum EUpdate {
	
	NAME("name"),
	
	PACK("pack"),
	
	STATE("state");
	
	String type;
	EUpdate(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
