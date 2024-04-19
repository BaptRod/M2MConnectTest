package be.anb.rimex.m2mconnect.view.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class History {
		private final StringProperty date;
		private final StringProperty type;
		private final StringProperty id;
		private final StringProperty initiator;
	
	public History(String date, String type, String id, String initiator) {
		this.date = new SimpleStringProperty(date);
		this.type = new SimpleStringProperty(type);
		this.id = new SimpleStringProperty(id);
		this.initiator = new SimpleStringProperty(initiator);
	}
	
	public String getDate() {
		return date.get();
	}
	
	public StringProperty dateProperty() {
		return date;
	}
	
	public void setDate(String date) {
		this.date.set(date);
	}
	
	public String getType() {
		return type.get();
	}
	
	public StringProperty typeProperty() {
		return type;
	}
	
	public void setType(String type) {
		this.type.set(type);
	}
	
	public String getId() {
		return id.get();
	}
	
	public StringProperty idProperty() {
		return id;
	}
	
	public void setId(String id) {
		this.id.set(id);
	}
	
	public String getInitiator() {
		return initiator.get();
	}
	
	public StringProperty initiatorProperty() {
		return initiator;
	}
	
	public void setInitiator(String initiator) {
		this.initiator.set(initiator);
	}
}
	

