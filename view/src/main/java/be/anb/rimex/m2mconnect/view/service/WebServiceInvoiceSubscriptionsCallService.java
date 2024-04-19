package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.InvoiceDetailsResponse;
import be.anb.rimex.m2mconnect.service.response.InvoiceLineResponse;
import be.anb.rimex.m2mconnect.service.response.InvoicesResponse;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceInvoiceSubscriptionsCallService extends Service<InvoiceDetailsResponse> {
	
	private int id;
	
	private int page;
	
	private int limit;
	
	private String search;
	
	public WebServiceInvoiceSubscriptionsCallService() {
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public void setPg(int page) {
		this.page = page;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	@Override
	protected Task<InvoiceDetailsResponse> createTask() {
		return new Task<InvoiceDetailsResponse>() {
			@Override
			protected InvoiceDetailsResponse call() throws Exception {
				try {
					InvoiceDetailsResponse result = ServiceRest.getAllSubscriptionsInInvoiceForUser(id, page, limit, search);
					return result;
				} catch (M2MSubscriptionsException exception) {
					throw exception;
				}
			}
		};
	}
}
