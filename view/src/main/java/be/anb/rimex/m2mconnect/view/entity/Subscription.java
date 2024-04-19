package be.anb.rimex.m2mconnect.view.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Subscription {
		private final StringProperty imsi;
		private final StringProperty iccid;
		private final StringProperty nsce;
		private final StringProperty msisdn;
	
		private final StringProperty status;
		private final StringProperty description;
	
	    private final StringProperty packId;
	
		private final StringProperty name;
	
		private final StringProperty dateFirst;
		
		private final StringProperty dateLastSubChange;
		
		
		
	
		private final StringProperty pin1;
		
		private final StringProperty pin2;
	
		
		private final StringProperty puk1;
		
	
		private final StringProperty puk2;
		
		private final StringProperty monthlyData;
	
		private final StringProperty monthlySms;
		
		private String descriptionNotTranslate;
		
		private User user;
	public Subscription(String imsi, String iccid, String nsce, String msisdn, String status, String description, String name, Long monthlyData, Long monthlySms, String descriptionNotTranslate) {
			this.imsi = new SimpleStringProperty(imsi);
			this.iccid = new SimpleStringProperty(iccid);
			this.nsce = new SimpleStringProperty(nsce);
			this.msisdn = new SimpleStringProperty(msisdn);
			this.status = new SimpleStringProperty(status);
			this.description =new SimpleStringProperty(description);
			this.name = new SimpleStringProperty(name);
			this.monthlyData = new SimpleStringProperty(monthlyData + "");
			this.monthlySms = new SimpleStringProperty(monthlySms + "");
			this.pin1 = new SimpleStringProperty();
			this.pin2 = new SimpleStringProperty();
			this.puk1 = new SimpleStringProperty();
			this.puk2 = new SimpleStringProperty();
			this.dateFirst = new SimpleStringProperty();
			this.dateLastSubChange = new SimpleStringProperty();
			this.packId = new SimpleStringProperty();
			this.descriptionNotTranslate = descriptionNotTranslate;
		}
	
	public User getUser() {
		return user;
	}
	
	public String getDescriptionNotTranslate() {
		return descriptionNotTranslate;
	}
	
	public void setDescriptionNotTranslate(String descriptionNotTranslate) {
		this.descriptionNotTranslate = descriptionNotTranslate;
	}
	
	public void setUser(User u) {
		this.user = u;
	}
	
	public String getPin1() {
		return pin1.get();
	}
	
	public String getPuk2() {
		return puk2.get();
	}
	
	public StringProperty puk2Property() {
		return puk2;
	}
	
	public String getDateFirst() {
		return dateFirst.get();
	}
	
	public StringProperty dateFirstProperty() {
		return dateFirst;
	}
	
	public void setDateFirst(String dateFirst) {
		this.dateFirst.set(dateFirst);
	}
	
	public String getDateLastSubChange() {
		return dateLastSubChange.get();
	}
	
	public StringProperty dateLastSubChangeProperty() {
		return dateLastSubChange;
	}
	
	public void setDateLastSubChange(String dateLastSubChange) {
		this.dateLastSubChange.set(dateLastSubChange);
	}
	
	
	
	public void setPuk2(String puk2) {
		this.puk2.set(puk2);
	}
	
	public String getPin2() {
		return pin2.get();
	}
	
	public StringProperty pin2Property() {
		return pin2;
	}
	
	public void setPin2(String pin2) {
		this.pin2.set(pin2);
	}
	
	
	public StringProperty pin1Property() {
		return pin1;
	}
	
	public String getPuk1() {
		return puk1.get();
	}
	
	public StringProperty puk1Property() {
		return puk1;
	}
	
	public void setPuk1(String puk1) {
		this.puk1.set(puk1);
	}
	
	public void setPin1(String pin1) {
		this.pin1.set(pin1);
	}
	
	public String getPackId() {
		return packId.get();
	}
	
	public StringProperty packIdProperty() {
		return packId;
	}
	
	public void setPackId(String packId) {
		this.packId.set(packId);
	}
	
	public String getMonthlyData() {
		return monthlyData.get();
	}
	
	public StringProperty monthlyDataProperty() {
		return monthlyData;
	}
	
	public void setMonthlyData(String monthlyData) {
		this.monthlyData.set(monthlyData);
	}
	
	public String getMonthlySms() {
		return monthlySms.get();
	}
	
	public StringProperty monthlySmsProperty() {
		return monthlySms;
	}
	
	public void setMonthlySms(String monthlySms) {
		this.monthlySms.set(monthlySms);
	}
	
	public String getImsi() {
		return imsi.get();
	}
	
	public StringProperty imsiProperty() {
		return imsi;
	}
	
	public void setImsi(String imsi) {
		this.imsi.set(imsi);
	}
	
	public String getIccid() {
		return iccid.get();
	}
	
	public StringProperty iccidProperty() {
		return iccid;
	}
	
	public void setIccid(String iccid) {
		this.iccid.set(iccid);
	}
	
	public String getNsce() {
		return nsce.get();
	}
	
	public StringProperty nsceProperty() {
		return nsce;
	}
	
	public void setNsce(String nsce) {
		this.nsce.set(nsce);
	}
	
	public String getMsisdn() {
		return msisdn.get();
	}
	
	public StringProperty msisdnProperty() {
		return msisdn;
	}
	
	public void setMsisdn(String msisdn) {
		this.msisdn.set(msisdn);
	}
	
	public String getStatus() {
		return status.get();
	}
	
	public StringProperty statusProperty() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status.set(status);
	}
	
	public String getDescription() {
		return description.get();
	}
	
	public StringProperty descriptionProperty() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public String getName() {
		return name.get();
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public void setName(String name) {
		this.name.set(name);
	}
}
	

