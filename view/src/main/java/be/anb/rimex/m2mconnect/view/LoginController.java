package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.AppLoginProperties;
import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.AppProperties;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MAuthException;
import be.anb.rimex.m2mconnect.service.response.AuthentificationResponse;
import be.anb.rimex.m2mconnect.view.entity.User;
import be.anb.rimex.m2mconnect.view.service.WebServiceLoginCallService;
import be.anb.rimex.m2mconnect.view.util.CryptAES;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.crypto.SecretKey;

public class LoginController implements IParameter{
	
	@FXML
	private ProgressIndicator loginIndicator;
	
	@FXML
	private TextField fieldMail;
	
	@FXML
	private PasswordField tokenField;
	
	@FXML
	private Label errorText;
	
	@FXML
	private Label version;
	
	@FXML
	private Button btnLogin;
	
	@FXML
	private CheckBox checkBoxRemember;
	
	
	
	private static User u ;
	
	private WebServiceLoginCallService webServiceLoginCallService;
	
	
	public void initialize() {
		u = new User();
		u.getMailProperty().bindBidirectional(fieldMail.textProperty());
		u.getTokenProperty().bindBidirectional(tokenField.textProperty());
		checkPreferences();
		errorText.setVisible(false);
		loginIndicator.setVisible(false);
		
		version.setText("V" + AppProperties.getInstance().getValueOfProperty(EProperty.APP_VERSION));
		
		initialiseService();
		
		Application.setChild(ENameView.LOGIN_VIEW);
		
		fieldMail.setOnKeyReleased(event -> {
			checkField();
		});
		fieldMail.focusedProperty().addListener((observable, oldValue, newValue) -> {
			checkField();
		});
		tokenField.setOnKeyReleased(event -> {
			checkField();
		});
		tokenField.focusedProperty().addListener((observable, oldValue, newValue) -> {
			checkField();
		});

	}
	
	
	
	@FXML
	protected void onClickLogin(ActionEvent event)  {
		if(u.getMailProperty().getValue().isEmpty() || u.getTokenProperty().getValue().isEmpty()){
			errorText.setText(ResourceLanguage.getInstance().getTranslation(EProperty.INVALID_PASSWORD_LOGIN_ERROR.getName()));
			errorText.setVisible(true);
		}
		
		else {
				loginIndicator.setVisible(true);
				webServiceLoginCallService.setEmail(u.getMailProperty().getValue());
				webServiceLoginCallService.setPassword(u.getTokenProperty().getValue());
			
			if (webServiceLoginCallService.getState() == Service.State.FAILED || webServiceLoginCallService.getState() == Service.State.CANCELLED) {
				webServiceLoginCallService.reset();
			}
				// Démarrer le Service
				webServiceLoginCallService.start();
				btnLogin.setDisable(true);
				savePreferences();
		}
	}
	
	public static User getU() {
		return u;
	}
	
	private boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(emailRegex);
	}
	
	private void initialiseService() {
		// Créer un Service
		webServiceLoginCallService = new WebServiceLoginCallService();
		onSuccess();
		onFaillure();
		
	}
	
	private void onSuccess(){
		webServiceLoginCallService.setOnSucceeded(e -> {
			btnLogin.setDisable(false);
			AuthentificationResponse result = webServiceLoginCallService.getValue();
			u.setName(result.getName());
			u.setIdUser(result.getId());
			Application.replaceChild(ENameView.SUBSCRIPTIONS_VIEW, u);
			
		});
	}
	
	private void onFaillure(){
		webServiceLoginCallService.setOnFailed(e -> {
			btnLogin.setDisable(false);
			M2MAuthException exception = (M2MAuthException) webServiceLoginCallService.getException();
			if(exception.getCode() == EM2MError.ACCESS_DENIED){
				errorText.setText(ResourceLanguage.getInstance().getTranslation(EProperty.ACCESS_DENIED.getName()));
			}
			else if(exception.getCode() == EM2MError.SERVER_ERROR){
				errorText.setText(ResourceLanguage.getInstance().getTranslation(EProperty.SERVER_ERROR.getName()));
			}
			else if(exception.getCode() == EM2MError.USER_NOT_FOUND){
				errorText.setText(ResourceLanguage.getInstance().getTranslation(EProperty.INVALID_PASSWORD_LOGIN_ERROR.getName()));
			}
			loginIndicator.setVisible(false);
			errorText.setVisible(true);
			
		});
	}
	
	private void savePreferences() {
		if (checkBoxRemember.isSelected()) {
			AppLoginProperties.getInstance().setEmail(u.getMailProperty().getValue());
			String result = CryptAES.encrypt(u.getTokenProperty().getValue());
			AppLoginProperties.getInstance().setPassword(result);
		} else {
			// Effacez les préférences si l'utilisateur ne souhaite pas se souvenir
			AppLoginProperties.getInstance().setEmail("");
			AppLoginProperties.getInstance().setPassword("");
		}
		
		// Enregistrez la préférence "Se souvenir de moi"
		AppLoginProperties.getInstance().setRememberMe(checkBoxRemember.isSelected());
	}
	
	private void checkPreferences(){
		if(AppLoginProperties.fileLoginExist()){
			u.setMailProperty(AppLoginProperties.getInstance().getEmail());
			u.setTokenProperty(CryptAES.decrypt(AppLoginProperties.getInstance().getPassword()));
			checkBoxRemember.setSelected(AppLoginProperties.getInstance().getRememberMe());
		}
		else{
			u.setMailProperty("");
			u.setTokenProperty("");
			
		}
		
		if(!u.getMailProperty().getValue().isEmpty() && !u.getTokenProperty().getValue().isEmpty()){
			btnLogin.setDisable(false);
		}
		else{
			btnLogin.setDisable(true);
		}
	}
	
	private void checkField(){
		if (u.getMailProperty().getValue().isEmpty() || u.getTokenProperty().getValue().isEmpty()) {
			btnLogin.setDisable(true);
		}
		else if(!u.getMailProperty().getValue().isEmpty() && !u.getTokenProperty().getValue().isEmpty()){
			btnLogin.setDisable(false);
		}
		else if (!isValidEmail(u.getMailProperty().getValue())) {
			btnLogin.setDisable(true);
			errorText.setText(ResourceLanguage.getInstance().getTranslation(EProperty.INVALID_EMAIL_FORMAT.getName()));
			errorText.setVisible(true);
		}
		
		else if (isValidEmail(u.getMailProperty().getValue()) && u.getTokenProperty().getValue().isEmpty()) {
			btnLogin.setDisable(true);
			errorText.setText("");
			errorText.setVisible(false);
		}
		else if (isValidEmail(u.getMailProperty().getValue()) && !u.getTokenProperty().getValue().isEmpty()) {
			btnLogin.setDisable(false);
			errorText.setText("");
			errorText.setVisible(false);
		}
	}
	
	@Override
	public void setParameter(Object o) {
	
	}
	
	
	
}

