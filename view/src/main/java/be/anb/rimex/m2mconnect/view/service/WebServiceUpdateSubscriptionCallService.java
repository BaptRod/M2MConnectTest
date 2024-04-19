package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MAuthException;
import be.anb.rimex.m2mconnect.service.response.AuthentificationResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionUpdateResponse;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceUpdateSubscriptionCallService extends Service<SubscriptionUpdateResponse> {
	
	private  String iccid;
	private  String update;
	
	private String value;
	
	public WebServiceUpdateSubscriptionCallService() {
	}
	
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	public void setUpdate(String update) {
		this.update = update;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	protected Task<SubscriptionUpdateResponse> createTask() {
		return new Task<SubscriptionUpdateResponse>() {
			@Override
			protected SubscriptionUpdateResponse call() throws Exception {
				try {
					SubscriptionUpdateResponse result = ServiceRest.updateSubscription(iccid, update, value);
					return result;
				}catch (M2MAuthException exception){
					throw exception;
				}
			}
		};
	}
}
