package be.anb.rimex.m2mconnect.view.service;

import be.anb.rimex.m2mconnect.service.ServiceRest;
import be.anb.rimex.m2mconnect.service.exception.M2MAuthException;
import be.anb.rimex.m2mconnect.service.response.AuthentificationResponse;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class WebServiceLoginCallService extends Service<AuthentificationResponse> {
	
	private  String email;
	private  String password;
	
	public WebServiceLoginCallService() {
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	
	public void setPassword(String password){
		this.password = password;
	}
	@Override
	protected Task<AuthentificationResponse> createTask() {
		return new Task<AuthentificationResponse>() {
			@Override
			protected AuthentificationResponse call() throws Exception {
				try {
					AuthentificationResponse result = ServiceRest.loginRestService(email, password);
					return result;
				}catch (M2MAuthException exception){
					throw exception;
				}
			}
		};
	}
}
