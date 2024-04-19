package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.AppLoginProperties;
import be.anb.rimex.m2mconnect.common.AppProperties;
import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import be.anb.rimex.m2mconnect.view.service.M2MUpdateService;
import be.anb.rimex.m2mconnect.view.util.CryptAES;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application extends javafx.application.Application {
	protected static final Logger logger = LogManager.getLogger(Application.class);
	
	private static Stage stage;
	
	private static Scene scene;
	
	private static ENameView child = ENameView.LOGIN_VIEW;
	
	private static Object objectCurrent;
	
	private static Locale l;
	
	private static BorderPane borderPane;
	
	
	@Override
    public void start(Stage stage) throws IOException {
		logger.info("Application démarée");
		M2MUpdateService.checkUpdates();
		AppLoginProperties.createFileLogin();
		String theme = AppLoginProperties.getInstance().getTheme();
		Application.setUserAgentStylesheet(getClass().getResource("/style/nord-" + theme + ".css").toExternalForm());
		l = stringToLocale(AppLoginProperties.getInstance().getLanguage());
		Node node =  loadView(ENameView.MAIN_VIEW, l, null);
		Node c = loadView(ENameView.LOGIN_VIEW, l, null);
		if(node instanceof BorderPane){
			borderPane = (BorderPane) node;
			borderPane.setCenter(c);
			scene = new Scene(borderPane);
		}
		else{
			ScrollPane p = (ScrollPane) node;
			BorderPane bp = (BorderPane) p.getContent();
			bp.setCenter(c);
			scene = new Scene(p);
		}
		
        stage.setTitle("M2M Connect");
		Image image = new Image(getClass().getResource("/images/M2MConnect.png").toExternalForm());
		stage.getIcons().add(image);
        stage.setScene(scene);
		stage.setMaximized(true);
        stage.show();
		Application.stage = stage;
	    widthListerner();
    }
	
	private void widthListerner(){
		scene.widthProperty().addListener((obs, oldWidth, newWidth) -> {
			
			if (newWidth.doubleValue() < 1200) {
				ScrollPane scrollPane = new ScrollPane();
				scrollPane.setFitToWidth(true);
				scrollPane.setFitToHeight(true);
				scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				scrollPane.setContent(borderPane);
				scene.setRoot(scrollPane);
				
			}
			else{
				borderPane = (BorderPane) loadView(ENameView.MAIN_VIEW, l, Application.getObjectCurrent());
				Node ch = loadView(child, l, objectCurrent);
				borderPane.setCenter(ch);
				scene.setRoot(borderPane);
			}
		});
	}
	
	public static void setTheme(String theme){
		AppLoginProperties.getInstance().setTheme(theme);
		Application.setUserAgentStylesheet(Application.class.getResource("/style/nord-" + theme + ".css").toExternalForm());
	}
	
	public static void setChild(ENameView c) {
		child = c;
	}
	
	public static Stage getStage(){
		return stage;
	}
	
	public static Scene getScene(){
		return scene;
	}
	
	public static void setScene(Scene sc){
		scene = sc;
	}
	
	public static Node loadView(Locale l, Object o) {
		Node n = null;
		n = checkObjectParam(o, child, l);
		if(n != null){
			return n;
		}
		try{
		n = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource(child.getNameView())), ResourceLanguage.getInstance().getResourceForLocale(l));
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return n;
	}
	
	public static Node loadView(ENameView eNameView, Locale l, Object o) {
		Node n = null;
		n = checkObjectParam(o, eNameView, l);
		if(n != null){
			return n;
		}
		try{
		n = FXMLLoader.load(Objects.requireNonNull(Application.class.getResource(eNameView.getNameView())), ResourceLanguage.getInstance().getResourceForLocale(l));
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return n;
	}
	
	public static void replaceChild(ENameView eNameView, Object o){
		Parent parent =  stage.getScene().getRoot();
		if(parent instanceof BorderPane){
			borderPane = (BorderPane) parent;
		}
		else{
			ScrollPane scrollPane = (ScrollPane) parent;
			borderPane = (BorderPane) scrollPane.getContent();
			
		}
		Node n = null;
		if(eNameView != null) {
			n = Application.loadView(eNameView, l, o);
		}
		else {
			n = Application.loadView(l, o);
		}
		
		borderPane.setCenter(n);
		stage.show();
	}
	
	private static Node checkObjectParam(Object o, ENameView eNameView, Locale l){
		if(o != null){
			FXMLLoader loader = new FXMLLoader(Application.class.getResource(eNameView.getNameView()));
			loader.setResources(ResourceLanguage.getInstance().getResourceForLocale(l));
			Parent node = null;
			try {
				node = loader.load();
				IParameter controller = loader.getController();
				objectCurrent = o;
				controller.setParameter(o);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
			return node;
		}
		else {
			return null;
		}
	}
	
	public static void showErrorAlert(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(ResourceLanguage.getInstance().getTranslation(EProperty.ERROR.getName()));
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.getButtonTypes().remove(ButtonType.CANCEL);
		alert.showAndWait();
	}
	
	public static boolean showAlert(String message, AlertType alertType, ButtonType... buttonTypes) {
		Alert alert = new Alert(alertType, message, buttonTypes);
		alert.initOwner(stage);
		stage.setIconified(false);
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && (result.get().equals(ButtonType.OK) || result.get().equals(ButtonType.YES));
	}
	
	public static void showConfirm(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(null);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.getButtonTypes().remove(ButtonType.CANCEL);
		alert.showAndWait();
	}
	
	public static Object getObjectCurrent() {
		return objectCurrent;
	}
	
	public static void setLocaleLanguage(Locale locale){
		AppLoginProperties.getInstance().setLanguage(locale.getLanguage());
		l = locale;
	}
	
	public static Locale getLocaleLanguage(){
		return l;
	}
	
	private static Locale stringToLocale(String localeString) {
		
		
			return new Locale(localeString, "");
	}
	
	public static void main(String[] args) {
		launch();
    }
	
	
}