package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionsStateResponse;
import be.anb.rimex.m2mconnect.view.entity.Subscription;
import be.anb.rimex.m2mconnect.view.entity.Subscriptions;
import be.anb.rimex.m2mconnect.view.entity.User;
import be.anb.rimex.m2mconnect.view.service.WebServiceSubscriptionsCallService;
import be.anb.rimex.m2mconnect.view.service.WebServiceSubscriptionsStateCallService;
import be.anb.rimex.m2mconnect.view.util.Translate;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class SubscriptionsController implements IParameter{
	@FXML
	private Button btnInvoices;
	@FXML
	private Button btnPrices;
	@FXML
	private TextField searchField;
	@FXML
	private Label nameClient;
	
	@FXML
	private TableView<Subscription> listSubscriptions;
	
	@FXML
	private TableColumn<Subscription, String> imsiColumn;
	
	@FXML
	private TableColumn<Subscription, String> iccidColumn;
	
	@FXML
	private TableColumn<Subscription, String> nsceColumn;
	
	@FXML
	private TableColumn<Subscription, String> msisdnColumn;
	
	@FXML
	private TableColumn<Subscription, String> statusColumn;
	
	@FXML
	private TableColumn<Subscription, String> descriptionColumn;
	
	@FXML
	private TableColumn<Subscription, String> nameColumn;
	
	@FXML
	private ImageView btnNext;
	
	@FXML
	private ImageView btnPrevious;
	
	@FXML
	private Label nbrTotal;
	
	@FXML
	private Label nbrActivate;
	
	@FXML
	private Label nbrBreak;
	
	@FXML
	private Label nbrDesactivate;
	
	
	@FXML
	private ProgressIndicator subscriptionsIndicator;
	
	@FXML
	private ProgressIndicator subscriptionsIndicator2;
	
	@FXML
	private Label subscriptionsEmpty;
	
	@FXML
	private Label numPage;
	
	private Subscriptions s;
	
	private int numberPage = 0;
	
	private WebServiceSubscriptionsCallService webServiceSubscriptionsCallService;
	
	private WebServiceSubscriptionsStateCallService webServiceSubscriptionsStateCallService;
	
	
	
	
	private int itemsPerPage = 50;
	
	private int currentPageIndex = 0;
	private int oldPageIndex = 0;
	private ObservableList<Subscription> data;
	private int idUser;
	
	private User u;
	
	private boolean initialService;
	
	private static Subscription selectedSubscription;
	
	private boolean search = false;
	
	public void initialize() {
		Application.setChild(ENameView.SUBSCRIPTIONS_VIEW);
		nameClient.setText(LoginController.getU().getName());
		searchField.setFocusTraversable(false);
		btnInvoices.setFocusTraversable(false);
		btnPrices.setFocusTraversable(false);
		
		configureColumns();
		subscriptionsIndicator2.setVisible(true);
		listSubscriptions.setDisable(true);
		subscriptionsEmpty.setText("");
		listSubscriptions.setPlaceholder(subscriptionsEmpty);
		
		data = FXCollections.observableArrayList();
		
	}
	
	@Override
	public void setParameter(Object o){
		u = (User) o;
		idUser = u.getIdUser();
		if(currentPageIndex == 0){
			btnPrevious.setDisable(true);
		}
		initialService = true;
		s = new Subscriptions();
		initialiseServiceSubscriptions();
		initialiseServiceState();
		startServiceSubscriptions(currentPageIndex, s.getSearch());
		startServiceState(s.getSearch());
		listenerTextField();
		
	}
	
	private void listenerTextField() {
		searchField.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null) {if (newValue.isEmpty() && searchField.isFocused() && search) {
					subscriptionsIndicator2.setVisible(true);
					currentPageIndex = 0;
					startServiceSubscriptions(currentPageIndex, s.getSearch());
					startServiceState(s.getSearch());
				}
				
				Application.getScene().setOnKeyPressed(event -> {
					if (event.getCode() == KeyCode.ENTER) {
						this.onClickSearch();
					}
				});
			}
		});
	}
	
	
	private void initialiseServiceSubscriptions() {
		// Créer un Service
		webServiceSubscriptionsCallService = new WebServiceSubscriptionsCallService();
		onSuccessSubscriptions();
		onFaillureSubscriptions();
		
	}
	
	private void initialiseServiceState() {
		// Créer un Service
		webServiceSubscriptionsStateCallService = new WebServiceSubscriptionsStateCallService();
		onSuccessState();
		onFaillureState();
		
	}
	
	private void onFaillureState() {
		webServiceSubscriptionsStateCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceSubscriptionsStateCallService.getException();
			subscriptionsIndicator2.setVisible(false);
			errorBusiness(exception);
		});
	}
	
	private void onSuccessState() {
		webServiceSubscriptionsStateCallService.setOnSucceeded(e -> {
			SubscriptionsStateResponse result = webServiceSubscriptionsStateCallService.getValue();
			nbrTotal.textProperty().bindBidirectional(s.nbrTotalProperty());
			nbrActivate.textProperty().bindBidirectional(s.nbrActivateProperty());
			nbrBreak.textProperty().bindBidirectional(s.nbrBreakProperty());
			nbrDesactivate.textProperty().bindBidirectional(s.nbrDesactivateProperty());
			numPage.textProperty().bindBidirectional(s.numPageProperty());
			searchField.textProperty().bindBidirectional(s.searchProperty());
			
			numberPage = (int) Math.ceil((double) result.getTotal() / itemsPerPage);
			s.setNumPage(currentPageIndex + 1 + "/" + numberPage);
			s.setNbrTotal(ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTIONS_TOTAL.getName()) + " " + result.getTotal());
			s.setNbrActivate(ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTIONS_ACTIVATE.getName()) + " " + result.getActivate());
			s.setNbrBreak(ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTIONS_BREAK.getName()) + " " + result.getPause());
			s.setNbrDesactivate(ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTIONS_DESACTIVATE.getName()) + " " +  result.getDesactivate());
			subscriptionsIndicator2.setVisible(false);
			listSubscriptions.setDisable(false);
		});
	}
	
	@FXML
	protected void onClickNext(MouseEvent event)  {
		oldPageIndex = currentPageIndex;
		btnPrevious.setDisable(false);
		initialService = false;
		
		btnNext.setDisable(true);
		int temp = currentPageIndex + 1;
		if(temp < numberPage) {
			currentPageIndex += 1;
			if(currentPageIndex < numberPage){
				subscriptionsIndicator.setVisible(true);
				listSubscriptions.setDisable(true);
				startServiceSubscriptions(currentPageIndex, s.getSearch());
			}
			
		}
	}
	
	@FXML
	protected void onClickPrevious(MouseEvent event)  {
		subscriptionsIndicator.setVisible(true);
		listSubscriptions.setDisable(true);
		oldPageIndex = currentPageIndex;
		if (currentPageIndex > 0) {
			currentPageIndex--;
		}
		
		if(currentPageIndex == 0){
			btnPrevious.setDisable(true);
		}
		startServiceSubscriptions(currentPageIndex, s.getSearch());
	}
	
	private void startServiceSubscriptions(int page, String search){
		if (webServiceSubscriptionsCallService.getState() == Service.State.FAILED || webServiceSubscriptionsCallService.getState() == Service.State.CANCELLED || webServiceSubscriptionsCallService.getState() == State.SUCCEEDED) {
			webServiceSubscriptionsCallService.reset();
		}
		webServiceSubscriptionsCallService.setIdUser(idUser);
		webServiceSubscriptionsCallService.setSearch(search);
		webServiceSubscriptionsCallService.setPage(page);
		webServiceSubscriptionsCallService.setLimit(itemsPerPage);
		if (webServiceSubscriptionsCallService.getState() != Service.State.RUNNING) {
			webServiceSubscriptionsCallService.start();
		}
	}
	
	private void startServiceState(String search){
		if (webServiceSubscriptionsStateCallService.getState() == Service.State.FAILED || webServiceSubscriptionsStateCallService.getState() == Service.State.CANCELLED || webServiceSubscriptionsStateCallService.getState() == State.SUCCEEDED) {
			webServiceSubscriptionsStateCallService.reset();
		}
		webServiceSubscriptionsStateCallService.setIdUser(idUser);
		webServiceSubscriptionsStateCallService.setSearch(search);
		if (webServiceSubscriptionsStateCallService.getState() != Service.State.RUNNING) {
			webServiceSubscriptionsStateCallService.start();
		}
	}
	
	private void configureColumns(){
		imsiColumn.setCellValueFactory(new PropertyValueFactory<>("imsi"));
		
		iccidColumn.setCellValueFactory(new PropertyValueFactory<>("iccid"));
		
		nsceColumn.setCellValueFactory(new PropertyValueFactory<>("nsce"));
		
		msisdnColumn.setCellValueFactory(new PropertyValueFactory<>("msisdn"));
		
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		setCenteredCellFactory(imsiColumn);
		setCenteredCellFactory(iccidColumn);
		setCenteredCellFactory(nsceColumn);
		setCenteredCellFactory(msisdnColumn);
		setCenteredCellFactory(statusColumn);
		setCenteredCellFactory(descriptionColumn);
		setCenteredCellFactory(nameColumn);
		
		eventOnColumn();
	}
	
	private void eventOnColumn(){
		listSubscriptions.setRowFactory(tv -> {
			TableRow<Subscription> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					selectedSubscription = row.getItem();
					selectedSubscription.setUser(u);
					Application.replaceChild(ENameView.SUBSCRIPTION_DETAILS_VIEW, selectedSubscription);
				}
			});
			return row;
		});
	}
	
	private void onSuccessSubscriptions(){
		webServiceSubscriptionsCallService.setOnSucceeded(e -> {
			data.clear();
			
			List<SubscriptionResponse> result = webServiceSubscriptionsCallService.getValue();
			
			if (result.isEmpty()) {
				subscriptionsEmpty.setText(ResourceLanguage.getInstance().getTranslation(EProperty.EMPTY.getName()));
				listSubscriptions.setPlaceholder(subscriptionsEmpty);
			}
			
			if(result.size() == itemsPerPage + 1){
				addDatas(false, itemsPerPage, result);
			}
			else{
				addDatas(true, result.size(), result);
			}
			
			updateTableView();
			numPage.setText(currentPageIndex + 1 + "/" + numberPage);
			if(!initialService) {
				listSubscriptions.setDisable(false);
			}
			
			btnNext.setDisable(false);
			subscriptionsIndicator.setVisible(false);
			
		});
	}
	
	private void addDatas(boolean btnState, int sizeData, List<SubscriptionResponse> results){
		btnNext.setDisable(btnState);
		String name = "";
		for (int i = 0; i < sizeData; i++) {
			if(!results.get(i).getName().equals("false")){
				name = results.get(i).getName();
			}
			else{
				name = "";
			}
			data.add(new Subscription(results.get(i).getImsi(), results.get(i).getIccid(), results.get(i).getNsce(), results.get(i).getMsisdn(), results.get(i).getState(),
				Translate.getTranslateForPack(results.get(i).getSubscriptionPackageDescription()), name, results.get(i).getMonthlyData(), results.get(i).getMonthlySms(), results.get(i).getSubscriptionPackageDescription()));
		}
	}
	
	
	
	
	private void onFaillureSubscriptions(){
		webServiceSubscriptionsCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceSubscriptionsCallService.getException();
			subscriptionsIndicator.setVisible(false);
			btnNext.setDisable(false);
			listSubscriptions.setDisable(false);
			errorBusiness(exception);
			
			
		});
	}
	
	
	private void errorBusiness(M2MSubscriptionsException exception){
		currentPageIndex = oldPageIndex;
		if(currentPageIndex == 0){
			btnPrevious.setDisable(true);
		}
		if(exception.getCode() == EM2MError.ORANGE_ERROR){
			//subscriptionsError.setText(ResourceLanguage.getInstance().getTranslation(EProperty.ORANGE_ERROR.getName()));
			Application.showErrorAlert(ResourceLanguage.getInstance().getTranslation(EProperty.ORANGE_ERROR.getName()));
		}
		else if(exception.getCode() == EM2MError.SERVER_ERROR){
			//subscriptionsError.setText(ResourceLanguage.getInstance().getTranslation(EProperty.SERVER_ERROR.getName()));
			Application.showErrorAlert(ResourceLanguage.getInstance().getTranslation(EProperty.SERVER_ERROR.getName()));
		}
	}
	
	
	
	
	private void updateTableView() {
		Platform.runLater(() -> {
			listSubscriptions.setItems(FXCollections.observableArrayList(data));
		    listSubscriptions.refresh();
		});
		
	}
	@FXML
	private void onLogout(MouseEvent event) {
		Application.setChild(ENameView.LOGIN_VIEW);
		Application.replaceChild(null, null);
	}
	
	private void setCenteredCellFactory(TableColumn<Subscription, String> column) {
		column.setCellFactory(new Callback<TableColumn<Subscription, String>, TableCell<Subscription, String>>() {
			@Override
			public TableCell<Subscription, String> call(TableColumn<Subscription, String> param) {
				return new TableCell<Subscription, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText(null);
						} else {
							
							if(item.equals("ACTIVE")){
								setText(ResourceLanguage.getInstance().getTranslation(EProperty.ACTIVE.getName()));
								setTextFill(Color.GREEN);
							}
							else if(item.equals("PAUSE")){
								setText(ResourceLanguage.getInstance().getTranslation(EProperty.BREAK.getName()));
								setTextFill(Color.ORANGE);
							}
							else if(item.equals("DEACTIVATED")){
								setText(ResourceLanguage.getInstance().getTranslation(EProperty.DESACTIVATE.getName()));
								setTextFill(Color.RED);
							}
							else{
								setText(item);
							}
							setAlignment(Pos.CENTER);
						}
					}
				};
			}
		});
	}
	
	@FXML
	public void onClickSearch() {
		search = true;
		subscriptionsIndicator2.setVisible(true);
		if(s.getSearch() == null || s.getSearch().equals("")){
			currentPageIndex = 0;
			startServiceSubscriptions(currentPageIndex, "");
			startServiceState("");
		}
		else {
			currentPageIndex = 0;
			startServiceSubscriptions(currentPageIndex, s.getSearch());
			startServiceState(s.getSearch());
		}
		
	}
	
	@FXML
	public void onClickInvoices(MouseEvent mouseEvent) {
		Application.setChild(ENameView.INVOICES_VIEW);
		Application.replaceChild(null, u);
	}
	
	@FXML
	public void onClickPrices(MouseEvent mouseEvent) {
		Application.setChild(ENameView.SUBSCRIPTIONS_PRICES);
		Application.replaceChild(null, u);
	}
}

