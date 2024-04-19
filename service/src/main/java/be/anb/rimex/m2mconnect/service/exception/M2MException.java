package be.anb.rimex.m2mconnect.service.exception;

public class M2MException extends Exception{
	
	private EM2MError code;
	
	public M2MException(String message, EM2MError code) {
		
		super(message);
		this.code = code;
	}
	
	public EM2MError getCode() {
		return code;
	}
	
}
