package be.anb.rimex.m2mconnect.service.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class InvoicesResponse {
	
	
	public static final String SERIALIZED_NAME_REFERENCE = "reference";
	@JsonProperty(SERIALIZED_NAME_REFERENCE)
	private String reference;
	
	public static final String SERIALIZED_NAME_SUBSCRIPTION_TOTAL = "total";
	@JsonProperty(SERIALIZED_NAME_SUBSCRIPTION_TOTAL)
	private float total;
	
	public static final String SERIALIZED_NAME_ACCOUNTING_INVOICE = "accounting_invoice";
	@JsonProperty(SERIALIZED_NAME_ACCOUNTING_INVOICE)
	private AccountingInvoice accounting_invoice;
	
	@JsonProperty("start_period")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date start_period;
	
	@JsonProperty("end_period")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date end_period;
	
	
	public InvoicesResponse(){
	
	}
	
	
	public InvoicesResponse(String reference, float total, AccountingInvoice accounting_invoice) {
		this.reference = reference;
		this.total = total;
		this.accounting_invoice = accounting_invoice;
	}
	
	public String getReference() {
		return reference;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public float getTotal() {
		return total;
	}
	
	public void setTotal(float total) {
		this.total = total;
	}
	
	public AccountingInvoice getAccounting_invoice() {
		return accounting_invoice;
	}
	
	public void setAccounting_invoice(AccountingInvoice accounting_invoice) {
		this.accounting_invoice = accounting_invoice;
	}
	
	public Date getStart_period() {
		return start_period;
	}
	
	public void setStart_period(Date start_period) {
		this.start_period = start_period;
	}
	
	public Date getEnd_period() {
		return end_period;
	}
	
	public void setEnd_period(Date end_period) {
		this.end_period = end_period;
	}
}
