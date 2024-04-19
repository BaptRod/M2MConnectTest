package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SubscriptionHistoryResponse {
	
	public static final String SERIALIZED_NAME_TIMESTAMP = "date";
	@JsonProperty(SERIALIZED_NAME_TIMESTAMP)
	private String date;
	
	public static final String SERIALIZED_NAME_SUBSCRIPTION_HISTORY_TYPE = "subscriptionHistoryType";
	@JsonProperty(SERIALIZED_NAME_SUBSCRIPTION_HISTORY_TYPE)
	private String subscriptionHistoryType;
	
	public static final String SERIALIZED_NAME_SERVICE_REQUEST_ID = "serviceRequestId";
	@JsonProperty(SERIALIZED_NAME_SERVICE_REQUEST_ID)
	private String serviceRequestId;
	
	public static final String SERIALIZED_NAME_INITIATOR_NAME = "initiatorName";
	@JsonProperty(SERIALIZED_NAME_INITIATOR_NAME)
	private String initiatorName;
	
	public SubscriptionHistoryResponse() {
	
	}
	
	public SubscriptionHistoryResponse(String date, String subscriptionHistoryType, String serviceRequestId,
		String initiatorName) {
		this.date = date;
		this.subscriptionHistoryType = subscriptionHistoryType;
		this.serviceRequestId = serviceRequestId;
		this.initiatorName = initiatorName;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getSubscriptionHistoryType() {
		return subscriptionHistoryType;
	}
	
	public void setSubscriptionHistoryType(String subscriptionHistoryType) {
		this.subscriptionHistoryType = subscriptionHistoryType;
	}
	
	public String getServiceRequestId() {
		return serviceRequestId;
	}
	
	public void setServiceRequestId(String serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}
	
	public String getInitiatorName() {
		return initiatorName;
	}
	
	public void setInitiatorName(String initiatorName) {
		this.initiatorName = initiatorName;
	}
}
