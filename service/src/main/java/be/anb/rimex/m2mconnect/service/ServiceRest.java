package be.anb.rimex.m2mconnect.service;
import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.AppProperties;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MAuthException;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.request.AuthentificationRequest;
import be.anb.rimex.m2mconnect.service.request.UpdateSubscriptionRequest;
import be.anb.rimex.m2mconnect.service.response.AuthentificationResponse;
import be.anb.rimex.m2mconnect.service.response.InvoiceDetailsResponse;
import be.anb.rimex.m2mconnect.service.response.InvoiceLineResponse;
import be.anb.rimex.m2mconnect.service.response.InvoicesResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionDetailsResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionHistoryResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionPackPriceResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionErrorResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionUpdateResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionsStateResponse;
import be.anb.rimex.m2mconnect.service.util.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRest {
	private static final String URL_API = AppProperties.getInstance().getValueOfProperty(EProperty.URL_SERVER);
	private static final String URL_AUTH = "/auth/signin";
	
	private static final String URL_SUBSCRIPTIONS_USER = "/subscriptions/user/";
	
	private static final String URL_SUBSCRIPTIONS_STATE = "/subscriptions/state/user/";
	
	private static final String URL_SUBSCRIPTION_DETAILS = "/subscriptions/";
	private static final String URL_SUBSCRIPTION_HISTORY = "/subscriptions/history/";
	
	private static final String URL_SUBSCRIPTION_UPDATE = "/subscriptions/update";
	
	private static final String URL_SUBSCRIPTION_PRICES = "/subscriptions/prices/";
	
	private static final String URL_INVOICES = "/invoices/";
	
	private static final String URL_INVOICE_SUBCRIPTION = "/invoices/id/";
	
	private static final String URL_INVOICE_SUBCRIPTION_ICCID = "/invoices/iccid/";
	
	private static String token = "";
	
	
	public static AuthentificationResponse loginRestService(String email, String password) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String urlAuth = URL_API + URL_AUTH;
				AuthentificationRequest authRequest = new AuthentificationRequest(email, password);
				String requestBody = JsonUtil.convertObjectToJson(authRequest);
				HttpResponse<String> response = postRequest(urlAuth, requestBody);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					AuthentificationResponse authentificationResponse = JsonUtil.convertJsonStringToObject(response.body(), AuthentificationResponse.class);
					if (authentificationResponse.getCode() == 1) {
						token = authentificationResponse.getToken();
						return authentificationResponse;
					} else if (authentificationResponse.getCode() == 2) {
						throw new M2MAuthException(authentificationResponse.getMessage(), EM2MError.USER_NOT_FOUND);
					} else {
						throw new M2MAuthException(authentificationResponse.getMessage(), EM2MError.ACCESS_DENIED);
					}
				} else {
					// Erreur serveur, réessayer
					retries++;
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MAuthException("Error server", EM2MError.SERVER_ERROR);
	}
	
	public static List<SubscriptionResponse> getAllSubscriptionsForUser(int idUser, int page, int limit, String search) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_SUBSCRIPTIONS_USER + idUser;
				Map<String, String> params = new HashMap<>();
				params.put("page", page + "");
				params.put("limit", limit + "");
				params.put("search", search);
				
				url = buildParams(url, params);
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					List<SubscriptionResponse> subscriptionResponses = objectMapper.readValue(response.body(), new TypeReference<List<SubscriptionResponse>>() {});
					return subscriptionResponses;
				} else {
					ObjectMapper objectMapper = new ObjectMapper();
					SubscriptionErrorResponse subscriptionsErrorResponse = objectMapper.readValue(response.body(), new TypeReference<SubscriptionErrorResponse>() {});
					if (subscriptionsErrorResponse.getCode() == 1) {
						throw new M2MSubscriptionsException(subscriptionsErrorResponse.getMessage(), EM2MError.SERVER_ERROR);
					}else{
						throw new M2MSubscriptionsException(subscriptionsErrorResponse.getMessage(), EM2MError.ORANGE_ERROR);
					}
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	public static SubscriptionsStateResponse getAllSubscriptionsState(int idUser, String search) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_SUBSCRIPTIONS_STATE + idUser;
				Map<String, String> params = new HashMap<>();
				params.put("search", search);
				url = buildParams(url, params);
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					SubscriptionsStateResponse subscriptionResponses = objectMapper.readValue(response.body(), new TypeReference<SubscriptionsStateResponse>() {});
					return subscriptionResponses;
				} else {
					ObjectMapper objectMapper = new ObjectMapper();
					SubscriptionErrorResponse subscriptionsErrorResponse = objectMapper.readValue(response.body(), new TypeReference<SubscriptionErrorResponse>() {});
					if (subscriptionsErrorResponse.getCode() == 1) {
						throw new M2MSubscriptionsException(subscriptionsErrorResponse.getMessage(), EM2MError.SERVER_ERROR);
					}else{
						throw new M2MSubscriptionsException(subscriptionsErrorResponse.getMessage(), EM2MError.ORANGE_ERROR);
					}
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	public static SubscriptionDetailsResponse getDetailsForSubscription(String iccid) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_SUBSCRIPTION_DETAILS + iccid;
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					SubscriptionDetailsResponse subscriptionDetailsResponse = objectMapper.readValue(response.body(), new TypeReference<SubscriptionDetailsResponse>() {});
					return subscriptionDetailsResponse;
				} else {
					ObjectMapper objectMapper = new ObjectMapper();
					SubscriptionErrorResponse subscriptionsErrorResponse = objectMapper.readValue(response.body(), new TypeReference<SubscriptionErrorResponse>() {});
					throw new M2MSubscriptionsException(subscriptionsErrorResponse.getMessage(), EM2MError.ORANGE_ERROR);
					
				}
			} catch (IOException e) {
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	public static List<SubscriptionPackPriceResponse> getPricesPackSubscriptions(int idUser) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_SUBSCRIPTION_PRICES + idUser;
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					List<SubscriptionPackPriceResponse> subscriptionPackPriceResponses = objectMapper.readValue(response.body(), new TypeReference<List<SubscriptionPackPriceResponse>>() {});
					return subscriptionPackPriceResponses;
				} else {
					ObjectMapper objectMapper = new ObjectMapper();
					SubscriptionErrorResponse subscriptionsErrorResponse = objectMapper.readValue(response.body(), new TypeReference<SubscriptionErrorResponse>() {});
					throw new M2MSubscriptionsException(subscriptionsErrorResponse.getMessage(), EM2MError.SERVER_ERROR);
					
				}
			} catch (IOException e) {
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	
	public static List<SubscriptionHistoryResponse> getHistoryForSubscription(String iccid) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_SUBSCRIPTION_HISTORY + iccid;
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					List<SubscriptionHistoryResponse> subscriptionHistoryResponse = objectMapper.readValue(response.body(), new TypeReference<List<SubscriptionHistoryResponse>>() {});
					return subscriptionHistoryResponse;
				} else {
					ObjectMapper objectMapper = new ObjectMapper();
					SubscriptionErrorResponse subscriptionsErrorResponse = objectMapper.readValue(response.body(), new TypeReference<SubscriptionErrorResponse>() {});
					throw new M2MSubscriptionsException(subscriptionsErrorResponse.getMessage(), EM2MError.ORANGE_ERROR);
					
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	public static List<InvoicesResponse> getAllInvoicesForUser(int idUser) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_INVOICES + idUser;
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					List<InvoicesResponse> invoicesResponse = objectMapper.readValue(response.body(), new TypeReference<List<InvoicesResponse>>() {});
					return invoicesResponse;
				} else {
					throw new M2MSubscriptionsException("Problème avec le serveur!", EM2MError.SERVER_ERROR);
					
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	public static List<InvoiceLineResponse> getAllSubscriptionsInInvoiceForIccid(String iccid) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_INVOICE_SUBCRIPTION_ICCID + iccid + "/subscriptions";
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					List<InvoiceLineResponse> invoiceLineResponses = objectMapper.readValue(response.body(), new TypeReference<List<InvoiceLineResponse>>() {});
					return invoiceLineResponses;
				} else {
					throw new M2MSubscriptionsException("Problème avec le serveur!", EM2MError.SERVER_ERROR);
					
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	
	public static InvoiceDetailsResponse getAllSubscriptionsInInvoiceForUser(int id, int page, int limit, String search) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_INVOICE_SUBCRIPTION + id + "/subscriptions";
				Map<String, String> params = new HashMap<>();
				params.put("page", page + "");
				params.put("limit", limit + "");
				params.put("search", search + "");
				url = buildParams(url, params);
				HttpResponse<String> response = getRequest(url);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					ObjectMapper objectMapper = new ObjectMapper();
					InvoiceDetailsResponse invoiceDetailsResponse = objectMapper.readValue(response.body(), new TypeReference<InvoiceDetailsResponse>() {});
					return invoiceDetailsResponse;
				} else {
					throw new M2MSubscriptionsException("Problème avec le serveur!", EM2MError.SERVER_ERROR);
					
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	public static SubscriptionUpdateResponse updateSubscription(String iccid, String update, String value) throws Exception{
		int maxRetries = 2;
		int retries = 0;
		
		while (retries < maxRetries) {
			try {
				String url = URL_API + URL_SUBSCRIPTION_UPDATE;
				UpdateSubscriptionRequest updateSubscriptionRequest = new UpdateSubscriptionRequest(iccid, update, value);
				String requestBody = JsonUtil.convertObjectToJson(updateSubscriptionRequest);
				HttpResponse<String> response = postRequest(url, requestBody);
				// Traiter la réponse du serveur
				if (response.statusCode() == 200) {
					 SubscriptionUpdateResponse resp = JsonUtil.convertJsonStringToObject(response.body(), SubscriptionUpdateResponse.class);
					 return resp;
				} else {
					// Erreur serveur, réessayer
					retries++;
				}
			} catch (IOException e) {
				// Erreur d'E/S, réessayer
				retries++;
			}
		}
		throw new M2MSubscriptionsException("Error server", EM2MError.SERVER_ERROR);
	}
	
	
	private static HttpResponse<String> postRequest(String url, String requestBody) throws IOException, InterruptedException {
		HttpRequest request ;
		if(token.equals("")) {
			request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		}
		else{
			request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Authorization", "Bearer " + token)
				.header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();
		}
		// Envoyer la requête et attendre la réponse
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
	
	private static HttpResponse<String> getRequest(String url) throws IOException, InterruptedException {
		HttpRequest request;
		if(token.equals("")) {
			request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();
		}
		else{
			request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("Authorization", "Bearer " + token)
				.GET()
				.build();
		}
		
		// Envoyer la requête et attendre la réponse
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
		return response;
	}
	
	
	
	private static String buildParams(String url, Map<String, String> params){
		StringBuilder newUrl = new StringBuilder(url);
		if(!params.isEmpty()) {
			int count = 0;
			for (Map.Entry<String,String> entry : params.entrySet()) {
				if (count == 0) newUrl.append("?");
				else newUrl.append("&");
				
				newUrl.append(entry.getKey());
				newUrl.append("=");
				newUrl.append(entry.getValue());
				
				count ++;
			}
		}
		return newUrl.toString();
	}
	
	
}
