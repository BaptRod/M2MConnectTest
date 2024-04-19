package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class InvoiceDetailsResponse {
	@JsonProperty("invoice_line_list")
	private List<InvoiceLineResponse> invoiceLineList;
	@JsonProperty("number_element")
	private int numberElement;
	
	public InvoiceDetailsResponse(List<InvoiceLineResponse> invoiceLineList, int numberElement) {
		this.invoiceLineList = invoiceLineList;
		this.numberElement = numberElement;
	}
	
	public InvoiceDetailsResponse(){
	
	}
	
	public List<InvoiceLineResponse> getInvoiceLineList() {
		return invoiceLineList;
	}
	
	public void setInvoiceLineList(List<InvoiceLineResponse> invoiceLineList) {
		this.invoiceLineList = invoiceLineList;
	}
	
	public int getNumberElement() {
		return numberElement;
	}
	
	public void setNumberElement(int numberElement) {
		this.numberElement = numberElement;
	}
}
