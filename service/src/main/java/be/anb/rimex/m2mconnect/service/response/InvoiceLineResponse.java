package be.anb.rimex.m2mconnect.service.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class InvoiceLineResponse {
	
	@JsonProperty("msisdn")
	private String msisdn;
	@JsonProperty("iccid")
	private String iccid;
	
	@JsonProperty("state")
	private String state;
	
	@JsonProperty("price_plan")
	private String price_plan;
	
	@JsonProperty("total")
	private float  total;
	
	@JsonProperty("traffic_kb")
	private float traffic_kb;
	
	@JsonProperty("number_of_sms")
	private int number_of_sms;
	
	@JsonProperty("duration")
	private int duration;
	
	@JsonProperty("accounting_invoice")
	private AccountingInvoice accounting_invoice;
	
	@JsonProperty("start_period")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date start_period;
	
	@JsonProperty("end_period")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
	private Date end_period;
	
	public InvoiceLineResponse(){
	
	}
	
	public AccountingInvoice getAccounting_invoice() {
		return accounting_invoice;
	}
	
	public Date getStart_period() {
		return start_period;
	}
	
	public Date getEnd_period() {
		return end_period;
	}
	
	public String getMsisdn() {
		return msisdn;
	}
	
	public String getIccid() {
		return iccid;
	}
	
	public String getState() {
		return state;
	}
	
	public String getPrice_plan() {
		return price_plan;
	}
	
	public float getTotal() {
		return total;
	}
	
	public float getTraffic_kb() {
		return traffic_kb;
	}
	
	public int getNumber_of_sms() {
		return number_of_sms;
	}
	
	public int getDuration() {
		return duration;
	}
}
