package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.InvoiceLineResponse;
import java.util.List;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceInvoiceSubscriptionsIccidCallService extends Service<List<InvoiceLineResponse>> {
	
	private String iccid;
	
	public WebServiceInvoiceSubscriptionsIccidCallService() {
	}
	
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	
	
	@Override
	protected Task<List<InvoiceLineResponse>> createTask() {
		return new Task<List<InvoiceLineResponse>>() {
			@Override
			protected List<InvoiceLineResponse> call() throws Exception {
				try {
					List<InvoiceLineResponse> result = ServiceRest.getAllSubscriptionsInInvoiceForIccid(iccid);
					return result;
				} catch (M2MSubscriptionsException exception) {
					throw exception;
				}
			}
		};
	}
}
