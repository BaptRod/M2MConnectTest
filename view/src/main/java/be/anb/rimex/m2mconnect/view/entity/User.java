package be.anb.rimex.m2mconnect.view.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
	
	private StringProperty mailProperty;
	private StringProperty tokenProperty;
	
	private int idUser;
	
	private String  name;
	
	public User(){
		this.mailProperty = new SimpleStringProperty();
		this.tokenProperty = new SimpleStringProperty();
	}
	
	public StringProperty mailPropertyProperty() {
		return mailProperty;
	}
	
	public void setMailProperty(String mailProperty) {
		this.mailProperty.set(mailProperty);
	}
	
	public StringProperty tokenPropertyProperty() {
		return tokenProperty;
	}
	
	public void setTokenProperty(String tokenProperty) {
		this.tokenProperty.set(tokenProperty);
	}
	
	public StringProperty getMailProperty() {
		return mailProperty;
	}
	
	public StringProperty getTokenProperty() {
		return tokenProperty;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getIdUser() {
		return idUser;
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
}
