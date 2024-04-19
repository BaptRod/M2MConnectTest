package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionsStateResponse;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceSubscriptionsStateCallService extends Service<SubscriptionsStateResponse> {
	
	private int idUser;
	
	private String search;
	
	public WebServiceSubscriptionsStateCallService() {
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	@Override
	protected Task<SubscriptionsStateResponse> createTask() {
		return new Task<SubscriptionsStateResponse>() {
			@Override
			protected SubscriptionsStateResponse call() throws Exception {
				try {
					SubscriptionsStateResponse result = ServiceRest.getAllSubscriptionsState(idUser, search);
					return result;
				} catch (M2MSubscriptionsException exception) {
					throw exception;
				}
			}
		};
	}
}
