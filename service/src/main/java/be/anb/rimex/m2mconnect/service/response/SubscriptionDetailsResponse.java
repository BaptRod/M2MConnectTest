package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SubscriptionDetailsResponse {
	
	public static final String SERIALIZED_NAME_FIRST_ACTIVATION_DATE = "first_activation_date";
	@JsonProperty(SERIALIZED_NAME_FIRST_ACTIVATION_DATE)
	
	private String firstActivationDate;
	
	public static final String SERIALIZED_NAME_ICC = "icc";
	@JsonProperty(SERIALIZED_NAME_ICC)
	private String icc;
	
	public static final String SERIALIZED_NAME_IMEI = "imei";
	@JsonProperty(SERIALIZED_NAME_IMEI)
	private String imei;
	
	public static final String SERIALIZED_NAME_IMSI = "imsi";
	@JsonProperty(SERIALIZED_NAME_IMSI)
	private String imsi;
	
	public static final String SERIALIZED_NAME_INSTALLATION_DATE = "installation_date";
	@JsonProperty(SERIALIZED_NAME_INSTALLATION_DATE)
	private String installationDate;
	
	public static final String SERIALIZED_NAME_LAST_SUBSCRIPTION_DATE_CHANGE = "last_subscription_date_change";
	@JsonProperty(SERIALIZED_NAME_LAST_SUBSCRIPTION_DATE_CHANGE)
	private String lastSubscriptionDateChange;
	public static final String SERIALIZED_NAME_MSISDN = "msisdn";
	@JsonProperty(SERIALIZED_NAME_MSISDN)
	private String msisdn;
	
	public static final String SERIALIZED_NAME_PBR_EXIT_DATE = "pbr_exit_date";
	@JsonProperty(SERIALIZED_NAME_PBR_EXIT_DATE)
	private String pbrExitDate;
	
	
	public static final String SERIALIZED_NAME_PIN1 = "pin1";
	@JsonProperty(SERIALIZED_NAME_PIN1)
	private String pin1;
	
	public static final String SERIALIZED_NAME_PIN2 = "pin2";
	@JsonProperty(SERIALIZED_NAME_PIN2)
	private String pin2;
	
	
	public static final String SERIALIZED_NAME_PRICE_PROFILE_NAME = "price_profile_name";
	@JsonProperty(SERIALIZED_NAME_PRICE_PROFILE_NAME)
	private String priceProfileName;
	
	public static final String SERIALIZED_NAME_PRODUCT_OFFER_NAME = "product_offer_name";
	@JsonProperty(SERIALIZED_NAME_PRODUCT_OFFER_NAME)
	private String productOfferName;
	
	public static final String SERIALIZED_NAME_PUK1 = "puk1";
	@JsonProperty(SERIALIZED_NAME_PUK1)
	private String puk1;
	
	public static final String SERIALIZED_NAME_PUK2 = "puk2";
	@JsonProperty(SERIALIZED_NAME_PUK2)
	private String puk2;
	
	public static final String SERIALIZED_NAME_SIM_SUBSCRIPTION_STATUS = "sim_subscription_status";
	@JsonProperty(SERIALIZED_NAME_SIM_SUBSCRIPTION_STATUS)
	private String simSubscriptionStatus;
	
	
	public static final String SERIALIZED_NAME_SMS_MO = "sms_mo";
	@JsonProperty(SERIALIZED_NAME_SMS_MO)
	private Boolean smsMo;
	
	public static final String SERIALIZED_NAME_SMS_MT = "sms_mt";
	@JsonProperty(SERIALIZED_NAME_SMS_MT)
	private Boolean smsMt;
	
	public static final String SERIALIZED_NAME_VOICE = "voice";
	@JsonProperty(SERIALIZED_NAME_VOICE)
	private Boolean voice;
	
	public static final String SERIALIZED_NAME_LTE = "lte";
	@JsonProperty(SERIALIZED_NAME_LTE)
	private Boolean lte;
	
		
	public static final String SERIALIZED_NAME_SUBSCRIPTION_PACKAGE_DESCRIPTION = "subscription_package_description";
	@JsonProperty(SERIALIZED_NAME_SUBSCRIPTION_PACKAGE_DESCRIPTION)
	private String subscriptionPackageDescription;
	
	
	public static final String SERIALIZED_NAME_ENABLE5G = "enable_5g";
	@JsonProperty(SERIALIZED_NAME_ENABLE5G)
	private Boolean enable5g;
	
	public SubscriptionDetailsResponse(){
	
	}
	
	public SubscriptionDetailsResponse(String firstActivationDate, String icc, String imei, String imsi, String installationDate,
		String lastSubscriptionDateChange, String msisdn, String pbrExitDate, String pin1, String pin2, String priceProfileName,
		String productOfferName, String puk1, String puk2, String simSubscriptionStatus, Boolean smsMo, Boolean smsMt,
		Boolean voice,
		Boolean lte, String subscriptionPackageDescription, Boolean enable5g) {
		this.firstActivationDate = firstActivationDate;
		this.icc = icc;
		this.imei = imei;
		this.imsi = imsi;
		this.installationDate = installationDate;
		this.lastSubscriptionDateChange = lastSubscriptionDateChange;
		this.msisdn = msisdn;
		this.pbrExitDate = pbrExitDate;
		this.pin1 = pin1;
		this.pin2 = pin2;
		this.priceProfileName = priceProfileName;
		this.productOfferName = productOfferName;
		this.puk1 = puk1;
		this.puk2 = puk2;
		this.simSubscriptionStatus = simSubscriptionStatus;
		this.smsMo = smsMo;
		this.smsMt = smsMt;
		this.voice = voice;
		this.lte = lte;
		this.subscriptionPackageDescription = subscriptionPackageDescription;
		this.enable5g = enable5g;
	}
	
	public String getFirstActivationDate() {
		return firstActivationDate;
	}
	
	public void setFirstActivationDate(String firstActivationDate) {
		this.firstActivationDate = firstActivationDate;
	}
	
	public String getIcc() {
		return icc;
	}
	
	public void setIcc(String icc) {
		this.icc = icc;
	}
	
	public String getImei() {
		return imei;
	}
	
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public String getImsi() {
		return imsi;
	}
	
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	
	public String getInstallationDate() {
		return installationDate;
	}
	
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
	
	public String getLastSubscriptionDateChange() {
		return lastSubscriptionDateChange;
	}
	
	public void setLastSubscriptionDateChange(String lastSubscriptionDateChange) {
		this.lastSubscriptionDateChange = lastSubscriptionDateChange;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
	public String getPbrExitDate() {
		return pbrExitDate;
	}
	
	public void setPbrExitDate(String pbrExitDate) {
		this.pbrExitDate = pbrExitDate;
	}
	
	public String getPin1() {
		return pin1;
	}
	
	public void setPin1(String pin1) {
		this.pin1 = pin1;
	}
	
	public String getPin2() {
		return pin2;
	}
	
	public void setPin2(String pin2) {
		this.pin2 = pin2;
	}
	
	public String getPriceProfileName() {
		return priceProfileName;
	}
	
	public void setPriceProfileName(String priceProfileName) {
		this.priceProfileName = priceProfileName;
	}
	
	public String getProductOfferName() {
		return productOfferName;
	}
	
	public void setProductOfferName(String productOfferName) {
		this.productOfferName = productOfferName;
	}
	
	public String getPuk1() {
		return puk1;
	}
	
	public void setPuk1(String puk1) {
		this.puk1 = puk1;
	}
	
	public String getPuk2() {
		return puk2;
	}
	
	public void setPuk2(String puk2) {
		this.puk2 = puk2;
	}
	
	public String getSimSubscriptionStatus() {
		return simSubscriptionStatus;
	}
	
	public void setSimSubscriptionStatus(String simSubscriptionStatus) {
		this.simSubscriptionStatus = simSubscriptionStatus;
	}
	
	public Boolean getSmsMo() {
		return smsMo;
	}
	
	public void setSmsMo(Boolean smsMo) {
		this.smsMo = smsMo;
	}
	
	public Boolean getSmsMt() {
		return smsMt;
	}
	
	public void setSmsMt(Boolean smsMt) {
		this.smsMt = smsMt;
	}
	
	public Boolean getVoice() {
		return voice;
	}
	
	public void setVoice(Boolean voice) {
		this.voice = voice;
	}
	
	public Boolean getLte() {
		return lte;
	}
	
	public void setLte(Boolean lte) {
		this.lte = lte;
	}
	
	public String getSubscriptionPackageDescription() {
		return subscriptionPackageDescription;
	}
	
	public void setSubscriptionPackageDescription(String subscriptionPackageDescription) {
		this.subscriptionPackageDescription = subscriptionPackageDescription;
	}
	
	public Boolean getEnable5g() {
		return enable5g;
	}
	
	public void setEnable5g(Boolean enable5g) {
		this.enable5g = enable5g;
	}
}
