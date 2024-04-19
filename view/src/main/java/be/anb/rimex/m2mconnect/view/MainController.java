package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainController implements IParameter{
	
	private static final Logger logger = LogManager.getLogger(MainController.class);
	
	
	@FXML
	private Menu config;
	
	@FXML
	private MenuItem fr;
	
	@FXML
	private MenuItem en;
	
	@FXML
	private MenuItem nl;
	
	@FXML
	private MenuItem de;
	
	public void initialize() {
		config.setGraphic(new ImageView(String.valueOf(getClass().getResource("/images/ic_settings.png"))));
		fr.setGraphic(new ImageView(String.valueOf(getClass().getResource("/images/french_flag.png"))));
		en.setGraphic(new ImageView(String.valueOf(getClass().getResource("/images/english_flag.png"))));
		nl.setGraphic(new ImageView(String.valueOf(getClass().getResource("/images/dutch_flag.png"))));
		de.setGraphic(new ImageView(String.valueOf(getClass().getResource("/images/german_flag.png"))));
		
	}
	
	
	@FXML
    protected void onChangeLanguageFr(ActionEvent event) {
		Application.setLocaleLanguage(Locale.FRENCH);
		loadLanguage((MenuItem) event.getSource());
    }
	
	@FXML
	protected void onChangeLanguageEn(ActionEvent event) {
		Application.setLocaleLanguage(Locale.ENGLISH);
		loadLanguage((MenuItem) event.getSource());
	}
	
	@FXML
	protected void onChangeLanguageNl(ActionEvent event) {
		Application.setLocaleLanguage(new Locale("nl", "NL"));
		loadLanguage((MenuItem) event.getSource());
	}
	
	@FXML
	protected void onChangeLanguageDe(ActionEvent event) {
		Application.setLocaleLanguage(Locale.GERMAN);
		loadLanguage((MenuItem) event.getSource());
	}
	
	private void loadLanguage( MenuItem item) {
			
			BorderPane parent = (BorderPane)Application.loadView(ENameView.MAIN_VIEW, Application.getLocaleLanguage(), null);
			Node child = Application.loadView(Application.getLocaleLanguage(), Application.getObjectCurrent());
			
			parent.setCenter(child);
			Stage stage = Application.getStage();
			stage.getScene().setRoot(parent);
			stage.show();
	}
	
	
	@FXML
	protected void onChangeThemeLight(ActionEvent event) {
		Application.setTheme("light");
	}
	
	@FXML
	protected void onChangeTemeDark(ActionEvent event) {
		Application.setTheme("dark");
	}
	
	@Override
	public void setParameter(Object o) {
	
	}
}