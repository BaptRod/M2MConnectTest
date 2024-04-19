package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MAuthException;
import be.anb.rimex.m2mconnect.service.response.SubscriptionDetailsResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionHistoryResponse;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceSubscriptionHistoryCallService extends Service<List<SubscriptionHistoryResponse>> {
	
	private  String iccid;
	
	public WebServiceSubscriptionHistoryCallService() {
	}
	
	
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	@Override
	protected Task<List<SubscriptionHistoryResponse>> createTask() {
		return new Task<List<SubscriptionHistoryResponse> >() {
			@Override
			protected List<SubscriptionHistoryResponse>  call() throws Exception {
				try {
					List<SubscriptionHistoryResponse> result = ServiceRest.getHistoryForSubscription(iccid);
					return result;
				}catch (M2MAuthException exception){
					throw exception;
				}
			}
		};
	}
}
