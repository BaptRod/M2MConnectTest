package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.InvoiceLineResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionDetailsResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionHistoryResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionUpdateResponse;
import be.anb.rimex.m2mconnect.view.entity.History;
import be.anb.rimex.m2mconnect.view.entity.InvoiceLine;
import be.anb.rimex.m2mconnect.view.entity.Subscription;
import be.anb.rimex.m2mconnect.view.service.WebServiceInvoiceSubscriptionsIccidCallService;
import be.anb.rimex.m2mconnect.view.service.WebServiceSubscriptionDetailsCallService;
import be.anb.rimex.m2mconnect.view.service.WebServiceSubscriptionHistoryCallService;
import be.anb.rimex.m2mconnect.view.service.WebServiceSubscriptionsCallService;
import be.anb.rimex.m2mconnect.view.service.WebServiceUpdateSubscriptionCallService;
import be.anb.rimex.m2mconnect.view.util.DateConverter;
import be.anb.rimex.m2mconnect.view.util.EPack;
import be.anb.rimex.m2mconnect.view.util.EUpdate;
import be.anb.rimex.m2mconnect.view.util.Translate;
import java.text.DecimalFormat;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class SubscriptionDetailsController implements IParameter{
	@FXML
	private ImageView btnRefresh;
	@FXML
	private TableView<InvoiceLine> listInvoice;
	@FXML
	private TableColumn<InvoiceLine, String> idColumn;
	@FXML
	private TableColumn<InvoiceLine, String> startColumn;
	@FXML
	private TableColumn<InvoiceLine, String> endColumn;
	@FXML
	private TableColumn<InvoiceLine, String> totalColumn;
	@FXML
	private TableColumn<InvoiceLine, String> trafficColumn;
	@FXML
	private TableColumn<InvoiceLine, String> smsColumn;
	@FXML
	private TableColumn<InvoiceLine, String> durationColumn;
	@FXML
	private ChoiceBox<String> selectedPack;
	@FXML
	private ImageView btnSavePack;
	@FXML
	private ImageView updatePackOk;
	@FXML
	private ImageView btnSaveState;
	@FXML
	private ChoiceBox<String> selectedState;
	@FXML
	private ImageView updateStateOk;
	@FXML
	private ImageView btnSaveName;
	@FXML
	private ImageView updateNameOk;
	@FXML
	private Pane editPane;
	@FXML
	private TextField newNameField;
	@FXML
	private Pane detailsPane;
	@FXML
	private  Label dateFirst;
	@FXML
	private Label name;
	@FXML
	private Label dateLastSubChange;
	@FXML
	private CheckBox lteCheck;
	@FXML
	private CheckBox voiceCheck;
	@FXML
	private CheckBox smsMoCheck;
	@FXML
	private CheckBox smsMtCheck;
	@FXML
	private CheckBox g5Check;
	@FXML
	private Label volumeData;
	@FXML
	private TextField smsNbr;
	@FXML
	private AnchorPane anchorPaneDetails;
	@FXML
	private ProgressIndicator indicatorDetails;
	@FXML
	private Label packId;
	@FXML
	private Label packDescription;
	@FXML
	private Label statut;
	private Subscription subscriptionSelected;
	
	@FXML
	private TableView<History> historyList;
	
	@FXML
	private TableColumn<History, String> historyDate;
	
	@FXML
	private TableColumn<History, String> historyType;
	
	@FXML
	private TableColumn<History, String> historyId;
	
	@FXML
	private TableColumn<History, String> historyInitiator;
	
	@FXML
	private TextField imsi;
	
	@FXML
	private TextField iccid;
	
	@FXML
	private TextField nsce;
	
	@FXML
	private TextField pin1;
	
	@FXML
	private TextField pin2;
	
	@FXML
	private TextField puk1;
	
	@FXML
	private TextField puk2;
	
	
	
	private WebServiceSubscriptionDetailsCallService webServiceSubscriptionDetailsCallService;
	private WebServiceInvoiceSubscriptionsIccidCallService webServiceInvoiceSubscriptionsIccidCallService;
	private WebServiceSubscriptionHistoryCallService webServiceSubscriptionHistoryCallService;
	
	private WebServiceUpdateSubscriptionCallService webServiceUpdateSubscriptionCallService;
	
	private ObservableList<History> data;
	
	private ObservableList<InvoiceLine> dataInvoice;
	
	private EUpdate update;
	
	private String stateNameBase = "";
	
	
	
	public void initialize() {
		Application.setChild(ENameView.SUBSCRIPTION_DETAILS_VIEW);
		selectedState.getItems().addAll( ResourceLanguage.getInstance().getTranslation(EProperty.ACTIVATE.getName()),
			ResourceLanguage.getInstance().getTranslation(EProperty.PAUSE.getName()));
		selectedState.setValue(ResourceLanguage.getInstance().getTranslation(EProperty.ACTIVATE.getName()));
		setItemSelectedPack();
		//historyList.setPlaceholder(new Label(""));
		listInvoice.setPlaceholder(new Label(""));
		anchorPaneDetails.setDisable(true);
		indicatorDetails.setVisible(true);
		updateNameOk.setVisible(false);
		updateStateOk.setVisible(false);
		updatePackOk.setVisible(false);
		
		configureColumns();
		dataInvoice = FXCollections.observableArrayList();
		
		
		
	}
	
	private void setItemSelectedPack() {
		
		for (EPack ePack : EPack.values()) {
			selectedPack.getItems().add(Translate.getTranslateForPack(ePack.getDescription()));
		}
		selectedPack.setValue(Translate.getTranslateForPack(EPack.SP_01.getDescription()));
	}
	
	@Override
	public void setParameter(Object o){
		subscriptionSelected = (Subscription) o;
		bindTextFields();
		initialiseServiceSubscriptionDetails();
		initialiseServiceSubscriptionUpdate();
		initialiseServiceInvoicesSubscription();
		//initialiseServiceSubscriptionHistory();
		startServiceDetailsSubscription();
		//startServiceHistorySubscription();
		startServiceInvoicesSubscriptions();
		setX(detailsPane);
		setX(editPane);
		setXTableView(listInvoice);
		
	}
	
	private void initialiseServiceSubscriptionUpdate() {
		webServiceUpdateSubscriptionCallService = new WebServiceUpdateSubscriptionCallService();
		onSuccessSubscriptionUpdate();
		onFaillureSubscriptionUpdate();
	}
	
	private void initialiseServiceInvoicesSubscription() {
		webServiceInvoiceSubscriptionsIccidCallService = new WebServiceInvoiceSubscriptionsIccidCallService();
		onSuccessInvoiceSubscriptions();
		onFaillureInvoiceSubscriptions();
	}
	
	private void startServiceInvoicesSubscriptions() {
		if (webServiceInvoiceSubscriptionsIccidCallService.getState() == Service.State.FAILED
			|| webServiceInvoiceSubscriptionsIccidCallService.getState() == Service.State.CANCELLED
			|| webServiceInvoiceSubscriptionsIccidCallService.getState() == State.SUCCEEDED) {
			webServiceInvoiceSubscriptionsIccidCallService.reset();
		}
		webServiceInvoiceSubscriptionsIccidCallService.setIccid(subscriptionSelected.getIccid());
		
		if (webServiceInvoiceSubscriptionsIccidCallService.getState() != Service.State.RUNNING) {
			webServiceInvoiceSubscriptionsIccidCallService.start();
		}
	}
	
	private void onSuccessInvoiceSubscriptions() {
		webServiceInvoiceSubscriptionsIccidCallService.setOnSucceeded(e -> {
			
			List<InvoiceLineResponse> result = webServiceInvoiceSubscriptionsIccidCallService.getValue();
			if(result.size() == 0){
				listInvoice.setPlaceholder(new Label(ResourceLanguage.getInstance().getTranslation(EProperty.INVOICES_SUBSCRIPTIONS_EMPTY.getName())));
			}
			for (int i = 0; i < result.size(); i++) {
				
				dataInvoice.add(new InvoiceLine(result.get(i).getMsisdn(), result.get(i).getIccid(), Translate.getTranslateState(result.get(i).getState()), Translate.getTranslateForPack(result.get(i).getPrice_plan()), result.get(i).getTotal() + "", result.get(i).getTraffic_kb() + "", result.get(i).getNumber_of_sms() + "", result.get(i).getDuration() + "", result.get(i).getAccounting_invoice().getName(),
					DateConverter.dateToString(result.get(i).getStart_period(), "dd-MM-yyyy"), DateConverter.dateToString(result.get(i).getEnd_period(), "dd-MM-yyyy")));
			}
			Platform.runLater(() -> {
				listInvoice.setItems(FXCollections.observableArrayList(dataInvoice));
				listInvoice.refresh();
			});
			listInvoice.setDisable(false);
			indicatorDetails.setVisible(false);
		});
	}
	
	
	private void onFaillureInvoiceSubscriptions(){
		webServiceInvoiceSubscriptionsIccidCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceInvoiceSubscriptionsIccidCallService.getException();
			anchorPaneDetails.setDisable(false);
			indicatorDetails.setVisible(false);
			errorBusiness(exception);
		});
	}
	
	
	
	private void setX(Pane pane){
		double windowWidth = Application.getStage().getWidth();
		double subPaneW = pane.getPrefWidth();
		double xO = (windowWidth - subPaneW) / 2;
		pane.setLayoutX(xO);
		
		Application.getStage().getScene().widthProperty().addListener((obs, oldWidth, newWidth) -> {
			String temp = newWidth + "";
			double subPaneWidth = pane.getPrefWidth();
			double xOffset = (Double.parseDouble(temp) - subPaneWidth) / 2;
			pane.setLayoutX(xOffset);
		});
	}
	
	private void setXTableView(TableView view){
		double windowWidth = Application.getStage().getWidth();
		double subPaneW = view.getPrefWidth();
		double xO = (windowWidth - subPaneW) / 2;
		view.setLayoutX(xO);
		
		Application.getStage().getScene().widthProperty().addListener((obs, oldWidth, newWidth) -> {
			String temp = newWidth + "";
			double subPaneWidth = view.getPrefWidth();
			double xOffset = (Double.parseDouble(temp) - subPaneWidth) / 2;
			view.setLayoutX(xOffset);
		});
	}
	
	private void bindTextFields() {
		imsi.textProperty().bindBidirectional(subscriptionSelected.imsiProperty());
		iccid.textProperty().bindBidirectional(subscriptionSelected.iccidProperty());
		nsce.textProperty().bindBidirectional(subscriptionSelected.nsceProperty());
		pin1.textProperty().bindBidirectional(subscriptionSelected.pin1Property());
		pin2.textProperty().bindBidirectional(subscriptionSelected.pin2Property());
		puk1.textProperty().bindBidirectional(subscriptionSelected.puk1Property());
		puk2.textProperty().bindBidirectional(subscriptionSelected.puk2Property());
		packId.textProperty().bindBidirectional(subscriptionSelected.packIdProperty());
		smsNbr.textProperty().bindBidirectional(subscriptionSelected.monthlySmsProperty());
		dateFirst.textProperty().bindBidirectional(subscriptionSelected.dateFirstProperty());
		dateLastSubChange.textProperty().bindBidirectional(subscriptionSelected.dateLastSubChangeProperty());
		volumeData.textProperty().bindBidirectional(subscriptionSelected.monthlyDataProperty());
		name.textProperty().bindBidirectional(subscriptionSelected.nameProperty());
		newNameField.textProperty().bindBidirectional(subscriptionSelected.nameProperty());
		
	}
	
	private void configureColumns() {
		/*
		historyDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		historyType.setCellValueFactory(new PropertyValueFactory<>("type"));
		historyId.setCellValueFactory(new PropertyValueFactory<>("id"));
		historyInitiator.setCellValueFactory(new PropertyValueFactory<>("initiator"));
		setCenteredCellFactory(historyDate);
		setCenteredCellFactory(historyType);
		setCenteredCellFactory(historyId);
		setCenteredCellFactory(historyInitiator);
		*/
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idInString"));
	    startColumn.setCellValueFactory(new PropertyValueFactory<>("startPeriod"));;
		endColumn.setCellValueFactory(new PropertyValueFactory<>("endPeriod"));;
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));;
		trafficColumn.setCellValueFactory(new PropertyValueFactory<>("traffic_kb"));;
		smsColumn.setCellValueFactory(new PropertyValueFactory<>("number_of_sms"));;
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));;
		setCenteredCellFactory(idColumn);
		setCenteredCellFactory(startColumn);
		setCenteredCellFactory(endColumn);
		setCenteredCellFactory(totalColumn);
		setCenteredCellFactory(trafficColumn);
		setCenteredCellFactory(smsColumn);
		setCenteredCellFactory(durationColumn);
		
	}
	
	private void startServiceHistorySubscription() {
		if (webServiceSubscriptionHistoryCallService.getState() == Service.State.FAILED
			|| webServiceSubscriptionHistoryCallService.getState() == Service.State.CANCELLED
			|| webServiceSubscriptionHistoryCallService.getState() == State.SUCCEEDED) {
			webServiceSubscriptionHistoryCallService.reset();
		}
		webServiceSubscriptionHistoryCallService.setIccid(subscriptionSelected.getIccid());
		
		if (webServiceSubscriptionHistoryCallService.getState() != Service.State.RUNNING) {
			webServiceSubscriptionHistoryCallService.start();
		}
	}
	
	
	private void startServiceDetailsSubscription() {
		if (webServiceSubscriptionDetailsCallService.getState() == Service.State.FAILED
			|| webServiceSubscriptionDetailsCallService.getState() == Service.State.CANCELLED
			|| webServiceSubscriptionDetailsCallService.getState() == State.SUCCEEDED) {
			webServiceSubscriptionDetailsCallService.reset();
		}
		webServiceSubscriptionDetailsCallService.setIccid(subscriptionSelected.getIccid());
		
		if (webServiceSubscriptionDetailsCallService.getState() != Service.State.RUNNING) {
			webServiceSubscriptionDetailsCallService.start();
		}
	}
	
	private void startServiceUpdateSubscription(String value, EUpdate update) {
		if (webServiceUpdateSubscriptionCallService.getState() == Service.State.FAILED
			|| webServiceUpdateSubscriptionCallService.getState() == Service.State.CANCELLED
			|| webServiceUpdateSubscriptionCallService.getState() == State.SUCCEEDED) {
			webServiceUpdateSubscriptionCallService.reset();
		}
		webServiceUpdateSubscriptionCallService.setIccid(subscriptionSelected.getIccid());
		webServiceUpdateSubscriptionCallService.setValue(value);
		webServiceUpdateSubscriptionCallService.setUpdate(update.getType());
		
		if (webServiceUpdateSubscriptionCallService.getState() != Service.State.RUNNING) {
			webServiceUpdateSubscriptionCallService.start();
		}
	}
	
	private void initialiseServiceSubscriptionDetails() {
		// Créer un Service
		webServiceSubscriptionDetailsCallService = new WebServiceSubscriptionDetailsCallService();
		onSuccessSubscriptionDetails();
		onFaillureSubscriptionDetails();
		
	}
	
	private void onSuccessSubscriptionDetails(){
		webServiceSubscriptionDetailsCallService.setOnSucceeded(e -> {
			anchorPaneDetails.setDisable(false);
			indicatorDetails.setVisible(false);
			SubscriptionDetailsResponse result = webServiceSubscriptionDetailsCallService.getValue();
			subscriptionSelected.setPin1(result.getPin1());
			subscriptionSelected.setPin2(result.getPin2());
			subscriptionSelected.setPuk1(result.getPuk1());
			subscriptionSelected.setPuk2(result.getPuk2());
			subscriptionSelected.setDateFirst(ResourceLanguage.getInstance().getTranslation(EProperty.DATE_FIRST.getName()) + " " + result.getFirstActivationDate());
			subscriptionSelected.setDateLastSubChange(ResourceLanguage.getInstance().getTranslation(EProperty.DATE_SUBCRIPTION_CHANGE.getName()) + " " + result.getLastSubscriptionDateChange());
			lteCheck.setSelected(result.getLte());
			g5Check.setSelected(result.getEnable5g());
			smsMoCheck.setSelected(result.getSmsMo());
			smsMtCheck.setSelected(result.getSmsMt());
			voiceCheck.setSelected(result.getVoice());
			subscriptionSelected.setPackId(result.getProductOfferName());
			packDescription.setText(Translate.getTranslateForPack(subscriptionSelected.getDescriptionNotTranslate()));
			statut.setText(Translate.getTranslateState(subscriptionSelected.getStatus()));
			subscriptionSelected.setName(subscriptionSelected.getName());
			subscriptionSelected.setMonthlyData(bytesToKilobytes(subscriptionSelected.getMonthlyData()));
			
		});
	}
	
	private void onSuccessSubscriptionUpdate(){
		webServiceUpdateSubscriptionCallService.setOnSucceeded(e -> {
			webServiceUpdateSubscriptionCallService.getValue();
			anchorPaneDetails.setDisable(false);
			indicatorDetails.setVisible(false);
			if(update == EUpdate.NAME){
				successView(updateNameOk, btnSaveName);
			}
			else if(update == EUpdate.STATE){
				successView(updateStateOk, btnSaveState);
				statut.setText(Translate.getTranslateState(getValueForChangeState()));
				subscriptionSelected.setStatus(getStatusCurrentNotTranslate());
			}
			else if(update == EUpdate.PACK){
				successView(updatePackOk, btnSavePack);
				String r = getValueForPackChange();
				subscriptionSelected.setPackId(r);
				packDescription.setText(Translate.getTranslateForPack(EPack.getDescriptionByCode(r)));
				subscriptionSelected.setDescription(EPack.getDescriptionByCode(packId.getText()));
				subscriptionSelected.setDescriptionNotTranslate(EPack.getDescriptionByCode(packId.getText()));
			}
			
		});
		
	}
	
	private void successView(ImageView view, ImageView b){
		view.setVisible(true);
		new Thread(() -> {
			try {
				Thread.sleep(1000);
				Platform.runLater(() -> {
					view.setVisible(false);
					b.setDisable(false);
				});
			} catch (InterruptedException exception) {
			
			}
		}).start();
		new Thread(() -> {
			try {
				Thread.sleep(4000);
				Platform.runLater(() -> {
					btnRefresh.setDisable(false);
				});
			} catch (InterruptedException exception) {
			
			}
		}).start();
	}
	
	public static String bytesToKilobytes(String bytes) {
		String t = bytes;
		if(bytes.length() > 1) {
			t = bytes.substring(0, bytes.length() - 2);
		}
		double b = Double.parseDouble(t);
		double r = b/1000.0;
		r = Math.round(r * 100.0) / 100.0;
		
		return r + "ko";
	}
	
	private void onFaillureSubscriptionDetails(){
		webServiceSubscriptionDetailsCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceSubscriptionDetailsCallService.getException();
			indicatorDetails.setVisible(false);
			errorBusiness(exception);
			
			
		});
	}
	
	private void onFaillureSubscriptionUpdate(){
		webServiceUpdateSubscriptionCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceUpdateSubscriptionCallService.getException();
			anchorPaneDetails.setDisable(false);
			indicatorDetails.setVisible(false);
			btnSaveName.setDisable(false);
			errorBusiness(exception);
			
			
		});
	}
	
	
	
	private void initialiseServiceSubscriptionHistory() {
		// Créer un Service
		webServiceSubscriptionHistoryCallService = new WebServiceSubscriptionHistoryCallService();
		onSuccessSubscriptionHistory();
		onFaillureSubscriptionHistory();
	}
	
	
	private void onSuccessSubscriptionHistory(){
		webServiceSubscriptionHistoryCallService.setOnSucceeded(e -> {
			List<SubscriptionHistoryResponse> result = webServiceSubscriptionHistoryCallService.getValue();
			for(SubscriptionHistoryResponse subscriptionHistoryResponse : result){
				data.add(new History(subscriptionHistoryResponse.getDate(), getTranslateForHistoryType(subscriptionHistoryResponse.getSubscriptionHistoryType()), subscriptionHistoryResponse.getServiceRequestId(), subscriptionHistoryResponse.getInitiatorName()));
				
			}
			Platform.runLater(() -> {
				historyList.setItems(FXCollections.observableArrayList(data));
				historyList.refresh();
			});
			indicatorDetails.setVisible(false);
			
		});
	}
	
	private void onFaillureSubscriptionHistory(){
		webServiceSubscriptionHistoryCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceSubscriptionHistoryCallService.getException();
			errorBusiness(exception);
			
			
		});
	}
	private void errorBusiness(M2MSubscriptionsException exception){
		if(exception.getCode() == EM2MError.ORANGE_ERROR) {
			
			Application.showErrorAlert(ResourceLanguage.getInstance().getTranslation(EProperty.ORANGE_ERROR.getName()));
			Application.setChild(ENameView.SUBSCRIPTIONS_VIEW);
			Application.replaceChild(null, subscriptionSelected.getUser());
		}
		else{
			Application.showErrorAlert(ResourceLanguage.getInstance().getTranslation(EProperty.SERVER_ERROR.getName()));
		}
		
	}
	
	
	@FXML
	private void onClickBack(MouseEvent event) {
		Application.setChild(ENameView.SUBSCRIPTIONS_VIEW);
		Application.replaceChild(null, subscriptionSelected.getUser());
	}
	
	private void setCenteredCellFactory(TableColumn<InvoiceLine, String> column) {
		column.setCellFactory(new Callback<TableColumn<InvoiceLine, String>, TableCell<InvoiceLine, String>>() {
			@Override
			public TableCell<InvoiceLine, String> call(TableColumn<InvoiceLine, String> param) {
				return new TableCell<InvoiceLine, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText(null);
						} else {
							setText(item);
							setAlignment(Pos.CENTER); // Alignement au centre
						}
					}
				};
			}
		});
	}
	
	private String getTranslateForHistoryType(String type){
		String result = "";
		switch (type) {
			case "CHANGE_EXISTING_SUBSCRIPTIONSTATE":
				result =  ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_24.getName());
				break;
			case "CHANGE_SUBSCRIPTIONOWNER":
				result =  ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_8.getName());
				break;
			case "CHANGE_SUBSCRIPTIONOWNER_EUICC":
				result =  ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_25.getName());
				break;
			case "CHANGE_SUBSCRIPTIONREGION":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_7.getName());
				break;
			case "CHANGE_SUBSCRIPTIONSTATE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_5.getName());
				break;
			case "CHANGE_SUBSCRIPTIONSTATE_EUICC":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_26.getName());
				break;
			case "CHANGE_SUBSCRIPTIONPACKAGE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_3.getName());
				break;
			case "CHANGE_SUBSCRIPTIONPACKAGE_EUICC":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_27.getName());
				break;
			case "CHANGE_SUBSCRIPTIONLABEL":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_6.getName());
				
				break;
			case "CHANGE_SUBSCRIPTIONNOTE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_28.getName());
				break;
			case "CHANGE_ALTROAMINGPROVIDER":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_29.getName());
				break;
			case "CREATE_SUBSCRIPTION":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_10.getName());
				break;
			case "CREATE_SUBSCRIPTION_EUICC":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_30.getName());
				break;
			case "THROTTLE_IMSI":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_1.getName());
				
				break;
			case "POLICYBASEDROUTING_EXIT":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_12.getName());
				
				break;
			case "TRIGGER_ACTION":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_2.getName());
				break;
			case "TIME_BASED_CHANGE_SUBSCRIPTIONSTATE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_31.getName());
				break;
			case "TIME_BASED_CHANGE_SUBSCRIPTIONPACKAGE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_4.getName());
				break;
			case "UPDATE_IMEI":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_13.getName());
				break;
			case "REASSIGN_MSISDN":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_19.getName());
				break;
			case "UPDATE_PROVISIONED_IMEI":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_14.getName());
				break;
			case "PCL_LIMIT_EXCEEDED":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_32.getName());
				break;
			case "CHANGE_SUBSCRIPTIONSTATE_AND_UNLINK_SUBSCRIPTION":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_33.getName());
				break;
			case "LOCK_SUBSCRIPTIONSTATE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_21.getName());
				break;
			case "UNLOCK_SUBSCRIPTIONSTATE":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_22.getName());
				break;
			case "UNKNOWN":
				result = ResourceLanguage.getInstance().getTranslation(EProperty.SUBSCRIPTION_HISTORY_23.getName());
				
				break;
			default:
				result = type;
				break;
		}
		return result;
	}
	
	
	@FXML
	private void onClickEditName(MouseEvent mouseEvent) {
		update = EUpdate.NAME;
		btnRefresh.setDisable(true);
		anchorPaneDetails.setDisable(true);
		indicatorDetails.setVisible(true);
		btnSaveName.setDisable(true);
		startServiceUpdateSubscription(subscriptionSelected.getName(), EUpdate.NAME);
		
	}
	
	@FXML
	private void onClickEditState(MouseEvent mouseEvent) {
		update = EUpdate.STATE;
		btnRefresh.setDisable(true);
		anchorPaneDetails.setDisable(true);
		indicatorDetails.setVisible(true);
		btnSaveState.setDisable(true);
		startServiceUpdateSubscription(getValueForChangeState(), EUpdate.STATE);
	}
	
	private String getValueForChangeState(){
		String result = "";
		int i = selectedState.getSelectionModel().getSelectedIndex();
		if(i == 0){
			return EProperty.ACTIVATE.getAction();
		}
		else if(i == 1){
			return EProperty.PAUSE.getAction();
		}
		return result;
	}
	
	@FXML
	private void onClickEditPack(MouseEvent mouseEvent) {
		update = EUpdate.PACK;
		btnRefresh.setDisable(true);
		anchorPaneDetails.setDisable(true);
		indicatorDetails.setVisible(true);
		btnSavePack.setDisable(true);
		startServiceUpdateSubscription(getValueForPackChange(), EUpdate.PACK);
	}
	
	private String getValueForPackChange(){
		int i = selectedPack.getSelectionModel().getSelectedIndex();
		int temp = i;
		
		if((temp + "").length() == 1){
			selectedPack.setValue(Translate.getTranslateForPack(EPack.getDescriptionByCode(EPack.BASE_PACK.getCode() + "0" + temp)));
			return EPack.BASE_PACK.getCode() + "0" + temp;
		}
		selectedPack.setValue(Translate.getTranslateForPack(EPack.getDescriptionByCode(EPack.BASE_PACK.getCode() + temp)));
		return EPack.BASE_PACK.getCode() + temp;
	}
	
	@FXML
	private void onClickRefresh(MouseEvent mouseEvent) {
		Application.setChild(ENameView.SUBSCRIPTION_DETAILS_VIEW);
		Application.replaceChild(null, subscriptionSelected);
	}
	
	private String getStatusCurrentNotTranslate(){
		if(statut.getText().charAt(0) == 'A'){
			return EProperty.ACTIVATE.getAction();
		}
		else{
			return EProperty.PAUSE.getAction();
		}
	}
}
