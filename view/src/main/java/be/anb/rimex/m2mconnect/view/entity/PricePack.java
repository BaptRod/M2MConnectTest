package be.anb.rimex.m2mconnect.view.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PricePack {
	
	private final StringProperty pack;
	private final StringProperty price;
	
	public PricePack(String pack, String price) {
		this.pack = new SimpleStringProperty(pack);
		this.price = new SimpleStringProperty(price);
	}
	
	public String getPack() {
		return pack.get();
	}
	
	public StringProperty packProperty() {
		return pack;
	}
	
	public void setPack(String pack) {
		this.pack.set(pack);
	}
	
	public String getPrice() {
		return price.get();
	}
	
	public StringProperty priceProperty() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price.set(price);
	}
}
