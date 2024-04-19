package be.anb.rimex.m2mconnect.view.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InvoiceLine {
	
	private final StringProperty msisdn;
	private final StringProperty iccid;
	private final StringProperty state;
	private final StringProperty price_plan;
	
	private final StringProperty total;
	private final StringProperty traffic_kb;
	
	private final StringProperty number_of_sms;
	
	private final StringProperty duration;
	
	private final StringProperty idInString;
	
	private final StringProperty startPeriod;
	
	private final StringProperty endPeriod;
	
	
	
	public InvoiceLine(String msisdn, String iccid, String state, String price_plan,
		String total, String traffic_kb, String number_of_sms, String duration, String idInString,
		String startPeriod, String endPeriod) {
		this.msisdn = new SimpleStringProperty(msisdn);
		this.iccid = new SimpleStringProperty(iccid);
		this.state = new SimpleStringProperty(state);
		this.price_plan = new SimpleStringProperty(price_plan);
		this.total = new SimpleStringProperty(total);
		this.traffic_kb = new SimpleStringProperty(traffic_kb);
		this.number_of_sms = new SimpleStringProperty(number_of_sms);
		this.duration = new SimpleStringProperty(duration);
		this.idInString = new SimpleStringProperty(idInString);
		this.startPeriod = new SimpleStringProperty(startPeriod);
		this.endPeriod = new SimpleStringProperty(endPeriod);
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
	
	public String getStartPeriod() {
		return startPeriod.get();
	}
	
	public StringProperty startPeriodProperty() {
		return startPeriod;
	}
	
	public void setStartPeriod(String startPeriod) {
		this.startPeriod.set(startPeriod);
	}
	
	public String getEndPeriod() {
		return endPeriod.get();
	}
	
	public StringProperty endPeriodProperty() {
		return endPeriod;
	}
	
	public void setEndPeriod(String endPeriod) {
		this.endPeriod.set(endPeriod);
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
	
	public String getIccid() {
		return iccid.get();
	}
	
	public StringProperty iccidProperty() {
		return iccid;
	}
	
	public void setIccid(String iccid) {
		this.iccid.set(iccid);
	}
	
	public String getState() {
		return state.get();
	}
	
	public StringProperty stateProperty() {
		return state;
	}
	
	public void setState(String state) {
		this.state.set(state);
	}
	
	public String getPrice_plan() {
		return price_plan.get();
	}
	
	public StringProperty price_planProperty() {
		return price_plan;
	}
	
	public void setPrice_plan(String price_plan) {
		this.price_plan.set(price_plan);
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
	
	public String getTraffic_kb() {
		return traffic_kb.get();
	}
	
	public StringProperty traffic_kbProperty() {
		return traffic_kb;
	}
	
	public void setTraffic_kb(String traffic_kb) {
		this.traffic_kb.set(traffic_kb);
	}
	
	public String getNumber_of_sms() {
		return number_of_sms.get();
	}
	
	public StringProperty number_of_smsProperty() {
		return number_of_sms;
	}
	
	public void setNumber_of_sms(String number_of_sms) {
		this.number_of_sms.set(number_of_sms);
	}
	
	public String getDuration() {
		return duration.get();
	}
	
	public StringProperty durationProperty() {
		return duration;
	}
	
	public void setDuration(String duration) {
		this.duration.set(duration);
	}
}
