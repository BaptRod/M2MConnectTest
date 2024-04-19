package be.anb.rimex.m2mconnect.common;

public enum EProperty {
	
	FILE_PATH_APPLICATION ("/application.properties"),
	
	FILE_PATH_LOGIN ("login.properties"),
	APP_VERSION ("app.version"),
	
	EMAIL_KEY ("app.email"),
	PASSWORD_KEY ("app.password"),
	REMEMBER_ME_KEY ("app.rememberMe"),
	
	THEME ("app.theme"),
	
	LANGUAGE ("app.language"),
	
	URL_SERVER ("url.server"),
	
	SERVER_ERROR("error.server.error"),
	
	ORANGE_ERROR("error.orange.error"),
	
	ACCESS_DENIED("error.access"),
	
	INVALID_EMAIL_FORMAT("error.invalid.format.email"),
	
	INVALID_PASSWORD_LOGIN_ERROR("error.invalid.password.or.email"),
	
	EMPTY("subscriptions.empty"),
	
	MB3_300SMS_CALL("subscription.pack.1"),
	
	MB1_100SMS_BE_ONLY("subscription.pack.2"),
	
	MB1_100SMS_30MIN_VOICE("subscription.pack.3"),
	
	MIN30_VOICE_OUT("subscription.pack.4"),
	
	MB25_50SMS_BE_ONLY("subscription.pack.5"),
	
	MIN15_VOICE_OUT("subscription.pack.6"),
	MB1_100SMS_15MIN_VOICE("subscription.pack.7"),
	
	MIN15_VOICE_30SMS("subscription.pack.8"),
	
	ACTIVE("subscription.state.active"),
	
	BREAK("subscription.state.break"),
	
	ERROR("subscriptions.error"),
	
	DATE_FIRST("subscription.date.first"),
	
	DATE_SUBCRIPTION_CHANGE("subscription.date.change"),
	
	DESACTIVATE("subscription.state.desactivate"),
	
	VOLUME("subscription.volume"),
	
	SUBSCRIPTION_HISTORY_1("subscription.history.1"),
	SUBSCRIPTION_HISTORY_2("subscription.history.2"),
	SUBSCRIPTION_HISTORY_3("subscription.history.3"),
	SUBSCRIPTION_HISTORY_4("subscription.history.4"),
	SUBSCRIPTION_HISTORY_5("subscription.history.5"),
	SUBSCRIPTION_HISTORY_6("subscription.history.6"),
	SUBSCRIPTION_HISTORY_7("subscription.history.7"),
	SUBSCRIPTION_HISTORY_8("subscription.history.8"),
	SUBSCRIPTION_HISTORY_9("subscription.history.9"),
	SUBSCRIPTION_HISTORY_10("subscription.history.10"),
	SUBSCRIPTION_HISTORY_11("subscription.history.11"),
	SUBSCRIPTION_HISTORY_12("subscription.history.12"),
	SUBSCRIPTION_HISTORY_13("subscription.history.13"),
	SUBSCRIPTION_HISTORY_14("subscription.history.14"),
	SUBSCRIPTION_HISTORY_15("subscription.history.15"),
	SUBSCRIPTION_HISTORY_16("subscription.history.16"),
	SUBSCRIPTION_HISTORY_17("subscription.history.17"),
	SUBSCRIPTION_HISTORY_18("subscription.history.18"),
	SUBSCRIPTION_HISTORY_19("subscription.history.19"),
	SUBSCRIPTION_HISTORY_20("subscription.history.20"),
	SUBSCRIPTION_HISTORY_21("subscription.history.21"),
	SUBSCRIPTION_HISTORY_22("subscription.history.22"),
	SUBSCRIPTION_HISTORY_23("subscription.history.23"),
	SUBSCRIPTION_HISTORY_24("subscription.history.24"),
	SUBSCRIPTION_HISTORY_25("subscription.history.25"),
	SUBSCRIPTION_HISTORY_26("subscription.history.26"),
	
	SUBSCRIPTION_HISTORY_27("subscription.history.27"),
	SUBSCRIPTION_HISTORY_28("subscription.history.28"),
	SUBSCRIPTION_HISTORY_29("subscription.history.29"),
	SUBSCRIPTION_HISTORY_30("subscription.history.30"),
	
	SUBSCRIPTION_HISTORY_31("subscription.history.31"),
	SUBSCRIPTION_HISTORY_32("subscription.history.32"),
	SUBSCRIPTION_HISTORY_33("subscription.history.33"),
	SUBSCRIPTION_PACK_DESCRIPTION("subscription.pack.description"),
	SUBSCRIPTION_STATUT("subscription.statut"),
	
	SUBSCRIPTIONS_TOTAL("subscriptions.total"),
	SUBSCRIPTIONS_ACTIVATE("subscriptions.activate"),
	SUBSCRIPTIONS_BREAK("subscriptions.break"),
	SUBSCRIPTIONS_DESACTIVATE ("subscriptions.desactivate"),
	SUBSCRIPTION_SEARCH_EMPTY("subscriptions.search.error"),
	INVOICES_ACTION_BUTTON("invoices.action.button"),
	
	INVOICES_ACTION_BUTTON_SUCCESS("invoices.action.button.sucess"),
	
	INVOICES_ACTION_BUTTON_ERROR("invoices.action.button.error"),
	
    INVOICES_SUBSCRIPTIONS_EMPTY("invoice.line.empty"),
	PAUSE("subscriptions.details.break", "PAUSE"),
	
	ACTIVATE("subscriptions.details.activate",  "ACTIVE"),
	
	FTP_URL("app.update.ftp.url"),
	
	FTP_USERNAME("app.update.ftp.username"),
	
	FTP_PASSWORD("app.update.ftp.mdp"),
	
	UPDATE_MESSAGE("update");
	
	
	private String name;
	
	private String action;
	EProperty(String s) {
		this.name = s;
	}
	
	EProperty(String s, String action) {
		this.name = s;
		this.action = action;
	}
	
	public String getName() {
		return name;
	}
	
	public String getAction() {
		return action;
	}
}
