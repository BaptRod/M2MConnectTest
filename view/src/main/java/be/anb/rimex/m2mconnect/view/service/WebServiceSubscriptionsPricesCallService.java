package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.SubscriptionPackPriceResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceSubscriptionsPricesCallService extends Service<List<SubscriptionPackPriceResponse>> {
	
	private int idUser;
	
	public WebServiceSubscriptionsPricesCallService() {
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	@Override
	protected Task<List<SubscriptionPackPriceResponse>> createTask() {
		return new Task<List<SubscriptionPackPriceResponse>>() {
			@Override
			protected List<SubscriptionPackPriceResponse> call() throws Exception {
				try {
					List<SubscriptionPackPriceResponse> result = ServiceRest.getPricesPackSubscriptions(idUser);
					return result;
				} catch (M2MSubscriptionsException exception) {
					throw exception;
				}
			}
		};
	}
}
