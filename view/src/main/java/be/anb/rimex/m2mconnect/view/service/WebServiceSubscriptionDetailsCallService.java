package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MAuthException;
import be.anb.rimex.m2mconnect.service.response.AuthentificationResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionDetailsResponse;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceSubscriptionDetailsCallService extends Service<SubscriptionDetailsResponse > {
	
	private  String iccid;
	
	public WebServiceSubscriptionDetailsCallService() {
	}
	
	
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	@Override
	protected Task<SubscriptionDetailsResponse > createTask() {
		return new Task<SubscriptionDetailsResponse >() {
			@Override
			protected SubscriptionDetailsResponse  call() throws Exception {
				try {
					SubscriptionDetailsResponse result = ServiceRest.getDetailsForSubscription(iccid);
					return result;
				}catch (M2MAuthException exception){
					throw exception;
				}
			}
		};
	}
}
