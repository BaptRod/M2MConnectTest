package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionResponse {
	
	public static final String SERIALIZED_NAME_ICCID = "iccid";
	@JsonProperty(SERIALIZED_NAME_ICCID)
	private String iccid;
	
	public static final String SERIALIZED_NAME_SUBSCRIPTION_NSCE = "nsce";
	@JsonProperty(SERIALIZED_NAME_SUBSCRIPTION_NSCE)
	private String nsce;
	
	public static final String SERIALIZED_NAME_SUBSCRIPTION_NAME = "name";
	@JsonProperty(SERIALIZED_NAME_SUBSCRIPTION_NAME)
	private String name;
	
	public static final String SERIALIZED_NAME_IMSI = "imsi";
	@JsonProperty(SERIALIZED_NAME_IMSI)
	private String imsi;
	
	public static final String SERIALIZED_NAME_MSISDN = "msisdn";
	@JsonProperty(SERIALIZED_NAME_MSISDN)
	private String msisdn;
	
	
	public static final String SERIALIZED_NAME_STATE = "state";
	@JsonProperty(SERIALIZED_NAME_STATE)
	private String state;
	
	public static final String SERIALIZED_NAME_SUBSCRIPTION_PACKAGE_DESCRIPTION = "subscriptionPackageDescription";
	@JsonProperty(SERIALIZED_NAME_SUBSCRIPTION_PACKAGE_DESCRIPTION)
	private String subscriptionPackageDescription;
	
	public static final String SERIALIZED_NAME_MONTHLY_DATA = "monthlyData";
	@JsonProperty(SERIALIZED_NAME_MONTHLY_DATA)
	private Long monthlyData;
	
	public static final String SERIALIZED_NAME_MONTHLY_SMS = "monthlySms";
	@JsonProperty(SERIALIZED_NAME_MONTHLY_SMS)
	private Long monthlySms;
	
	public SubscriptionResponse(){
	
	}
	
	
	public SubscriptionResponse(String iccid, String nsce, String name, String imsi, String msisdn, String state,
		String subscriptionPackageDescription, Long monthlyData, Long monthlySms) {
		this.iccid = iccid;
		this.nsce = nsce;
		this.name = name;
		this.imsi = imsi;
		this.msisdn = msisdn;
		this.state = state;
		this.subscriptionPackageDescription = subscriptionPackageDescription;
		this.monthlyData = monthlyData;
		this.monthlySms = monthlySms;
	}
	
	public String getIccid() {
		return iccid;
	}
	
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	public String getNsce() {
		return nsce;
	}
	
	public void setNsce(String nsce) {
		this.nsce = nsce;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getImsi() {
		return imsi;
	}
	
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getSubscriptionPackageDescription() {
		return subscriptionPackageDescription;
	}
	
	public void setSubscriptionPackageDescription(String subscriptionPackageDescription) {
		this.subscriptionPackageDescription = subscriptionPackageDescription;
	}
	
	public Long getMonthlyData() {
		return monthlyData;
	}
	
	public void setMonthlyData(Long monthlyData) {
		this.monthlyData = monthlyData;
	}
	
	public Long getMonthlySms() {
		return monthlySms;
	}
	
	public void setMonthlySms(Long monthlySms) {
		this.monthlySms = monthlySms;
	}
}
