package be.anb.rimex.m2mconnect.view;

public enum ENameView {
	MAIN_VIEW("main-view.fxml"),
	LOGIN_VIEW("login-view.fxml"),
	
	SUBSCRIPTION_DETAILS_VIEW("subscription-details-view.fxml"),
	SUBSCRIPTIONS_VIEW("subscriptions-view.fxml"),
	
	INVOICES_VIEW("invoices-view.fxml"),
	
	SUBSCRIPTIONS_PRICES("subscriptions-prices-view.fxml"),
	
	INVOICE_DETAILS_VIEW("invoice-details-view.fxml");
	
	
	
	private String nameView;
	ENameView(String s) {
		this.nameView = s;
	}
	
	public String getNameView() {
		return nameView;
	}
}
