package be.anb.rimex.m2mconnect.view.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Invoice {
	
	private final StringProperty reference;
	private final StringProperty client;
	private final StringProperty idInString;
	private final StringProperty total;
	
	private final StringProperty start;
	
	private final StringProperty end;
	
	private int id;
	
	private User u;
	
	
	
	public Invoice(String reference, String client, String idInString, String total, int id, String start, String end) {
		this.reference = new SimpleStringProperty(reference);
		this.client = new SimpleStringProperty(client);
		this.idInString = new SimpleStringProperty(idInString);
		this.total = new SimpleStringProperty(total);
		this.start = new SimpleStringProperty(start);
		this.end = new SimpleStringProperty(end);
		this.id = id;
	}
	
	public String getEnd() {
		return end.get();
	}
	
	public StringProperty endProperty() {
		return end;
	}
	
	public void setEnd(String end) {
		this.end.set(end);
	}
	
	public String getStart() {
		return start.get();
	}
	
	public StringProperty startProperty() {
		return start;
	}
	
	public void setStart(String start) {
		this.start.set(start);
	}
	
	public User getUser() {
		return u;
	}
	
	public void setUser(User u) {
		this.u = u;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getReference() {
		return reference.get();
	}
	
	public StringProperty referenceProperty() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference.set(reference);
	}
	
	public String getClient() {
		return client.get();
	}
	
	public StringProperty clientProperty() {
		return client;
	}
	
	public void setClient(String client) {
		this.client.set(client);
	}
	
	public String getIdInString() {
		return idInString.get();
	}
	
	public StringProperty idInStringProperty() {
		return idInString;
	}
	
	public void setIdInString(String idInString) {
		this.idInString.set(idInString);
	}
	
	public String getTotal() {
		return total.get();
	}
	
	public StringProperty totalProperty() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total.set(total);
	}
}
