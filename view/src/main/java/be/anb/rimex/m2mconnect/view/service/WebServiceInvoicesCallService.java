package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.InvoicesResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceInvoicesCallService extends Service<List<InvoicesResponse>> {
	
	private int idUser;
	
	public WebServiceInvoicesCallService() {
	}
	
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	
	@Override
	protected Task<List<InvoicesResponse>> createTask() {
		return new Task<List<InvoicesResponse>>() {
			@Override
			protected List<InvoicesResponse> call() throws Exception {
				try {
					List<InvoicesResponse> result = ServiceRest.getAllInvoicesForUser(idUser);
					return result;
				} catch (M2MSubscriptionsException exception) {
					throw exception;
				}
			}
		};
	}
}
