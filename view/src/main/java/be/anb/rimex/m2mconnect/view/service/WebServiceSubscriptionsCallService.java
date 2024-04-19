package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MAuthException;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.AuthentificationResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceSubscriptionsCallService extends Service<List<SubscriptionResponse>> {
	
	private String search;
	private int idUser;
	private int page;
	private int limit;
	
	public WebServiceSubscriptionsCallService() {
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	@Override
	protected Task<List<SubscriptionResponse>> createTask() {
		return new Task<List<SubscriptionResponse>>() {
			@Override
			protected List<SubscriptionResponse> call() throws Exception {
				try {
					List<SubscriptionResponse> result = ServiceRest.getAllSubscriptionsForUser(idUser, page, limit, search);
					return result;
				} catch (M2MSubscriptionsException exception) {
					throw exception;
				}
			}
		};
	}
}
