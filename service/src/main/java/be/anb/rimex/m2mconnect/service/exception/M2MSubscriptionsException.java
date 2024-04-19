package be.anb.rimex.m2mconnect.service.exception;

public class M2MSubscriptionsException extends RuntimeException {
	
	private EM2MError code;
	
	public M2MSubscriptionsException(String message, EM2MError code) {
		
		super(message);
		this.code = code;
	}
	
	public EM2MError getCode() {
		return code;
	}
}
