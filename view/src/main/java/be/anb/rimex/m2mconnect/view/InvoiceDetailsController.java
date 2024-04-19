package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.ExcelExporter;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.InvoiceDetailsResponse;
import be.anb.rimex.m2mconnect.service.response.InvoiceLineResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionResponse;
import be.anb.rimex.m2mconnect.view.entity.Invoice;
import be.anb.rimex.m2mconnect.view.entity.InvoiceLine;
import be.anb.rimex.m2mconnect.view.entity.Subscription;
import be.anb.rimex.m2mconnect.view.entity.Subscriptions;
import be.anb.rimex.m2mconnect.view.service.WebServiceInvoiceSubscriptionsCallService;
import be.anb.rimex.m2mconnect.view.util.Translate;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class InvoiceDetailsController implements IParameter{
	@FXML
	private TextField searchField;
	@FXML
	private Label numberSubscriptions;
	@FXML
	private Label numPage;
	@FXML
	private TableView<InvoiceLine> listInvoiceLine;
	@FXML
	private TableColumn<InvoiceLine, String> msisdnColumn;
	@FXML
	private TableColumn<InvoiceLine, String> iccidColumn;
	@FXML
	private TableColumn<InvoiceLine, String> statusColumn;
	@FXML
	private TableColumn<InvoiceLine, String> descriptionColumn;
	@FXML
	private TableColumn<InvoiceLine, String> totalColumn;
	@FXML
	private TableColumn<InvoiceLine, String> trafficColumn;
	@FXML
	private TableColumn<InvoiceLine, String> smsColumn;
	@FXML
	private TableColumn<InvoiceLine, String> durationColumn;
	@FXML
	private ProgressIndicator subscriptionsIndicator;
	@FXML
	private ImageView btnPrevious;
	@FXML
	private ImageView btnNext;
	
	private String search = "";
	
	private boolean isSearch = false;
	
	private int numberPage = 0;
	
	private int itemsPerPage = 50;
	
	private int currentPageIndex = 0;
	private int oldPageIndex = 0;
	
	private ObservableList<InvoiceLine> data;
	
	private WebServiceInvoiceSubscriptionsCallService webServiceInvoiceSubscriptionsCallService;
	
	private Invoice invoiceSelected;
	
	public void initialize() {
		Application.setChild(ENameView.INVOICE_DETAILS_VIEW);
		searchField.setFocusTraversable(false);
		listInvoiceLine.setPlaceholder(new Label(""));
		data = FXCollections.observableArrayList();
		listInvoiceLine.setDisable(true);
		subscriptionsIndicator.setVisible(true);
		configureColumns();
		
	}
	
	
	@Override
	public void setParameter(Object o) {
		invoiceSelected = (Invoice) o;
		if(currentPageIndex == 0){
			btnPrevious.setDisable(true);
		}
		initialiseServiceInvoiceSubscriptions();
		startServiceInvoiceSubscription(invoiceSelected.getId(), search);
		listenerTextField();
	}
	
	private void listenerTextField() {
		searchField.textProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue != null) {if (newValue.isEmpty() && searchField.isFocused() && isSearch) {
				subscriptionsIndicator.setVisible(true);
				currentPageIndex = 0;
				search = "";
				startServiceInvoiceSubscription(invoiceSelected.getId(), search);
			}
				
				Application.getScene().setOnKeyPressed(event -> {
					if (event.getCode() == KeyCode.ENTER) {
						this.onClickSearch();
					}
				});
			}
		});
	}
	
	
	
	private void initialiseServiceInvoiceSubscriptions() {
		webServiceInvoiceSubscriptionsCallService = new WebServiceInvoiceSubscriptionsCallService();
		onSuccessInvoiceSubscriptions();
		onFaillureInvoiceSubscriptions();
	}
	
	private void onSuccessInvoiceSubscriptions() {
		webServiceInvoiceSubscriptionsCallService.setOnSucceeded(e -> {
			data.clear();
			InvoiceDetailsResponse result = webServiceInvoiceSubscriptionsCallService.getValue();
			numberSubscriptions.setText(result.getNumberElement() + "");
			numberPage = (int) Math.ceil((double) result.getNumberElement() / itemsPerPage);
			numPage.setText(currentPageIndex + 1 + "/" + numberPage);
			List<InvoiceLineResponse> invoiceLineResponses = result.getInvoiceLineList();
			if(invoiceLineResponses.size() == itemsPerPage + 1){
				addDatas(false, itemsPerPage, invoiceLineResponses);
			}
			else{
				addDatas(true, invoiceLineResponses.size(), invoiceLineResponses);
			}
			updateTableView();
			listInvoiceLine.setDisable(false);
			subscriptionsIndicator.setVisible(false);
		});
	}
	
	private void addDatas(boolean btnState, int sizeData, List<InvoiceLineResponse> result){
		btnNext.setDisable(btnState);
		for (int i = 0; i < sizeData; i++) {
			data.add(new InvoiceLine(result.get(i).getMsisdn(), result.get(i).getIccid(), Translate.getTranslateState(result.get(i).getState()), Translate.getTranslateForPack(result.get(i).getPrice_plan()), result.get(i).getTotal() + "", result.get(i).getTraffic_kb() + "", result.get(i).getNumber_of_sms() + "", result.get(i).getDuration() + "", "", "", ""));
		}
	}
	
	private void onFaillureInvoiceSubscriptions() {
		webServiceInvoiceSubscriptionsCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceInvoiceSubscriptionsCallService.getException();
			subscriptionsIndicator.setVisible(false);
			btnNext.setDisable(false);
			listInvoiceLine.setDisable(false);
			errorBusiness(exception, ENameView.INVOICES_VIEW);
		});
	}
	
	private void errorBusiness(M2MSubscriptionsException exception, ENameView eNameView){
		currentPageIndex = oldPageIndex;
		if(currentPageIndex == 0){
			btnPrevious.setDisable(true);
		}
		if(exception.getCode() == EM2MError.SERVER_ERROR) {
			Application.showErrorAlert(ResourceLanguage.getInstance().getTranslation(EProperty.SERVER_ERROR.getName()));
			Application.setChild(eNameView);
			Application.replaceChild(null, invoiceSelected.getUser());
		}
	}
	
	private void updateTableView() {
		Platform.runLater(() -> {
			listInvoiceLine.setItems(FXCollections.observableArrayList(data));
			listInvoiceLine.refresh();
		});
		
	}
	
	private void startServiceInvoiceSubscription(int id, String search) {
		if (webServiceInvoiceSubscriptionsCallService.getState() == Service.State.FAILED
			|| webServiceInvoiceSubscriptionsCallService.getState() == Service.State.CANCELLED
			|| webServiceInvoiceSubscriptionsCallService.getState() == State.SUCCEEDED) {
			webServiceInvoiceSubscriptionsCallService.reset();
		}
		webServiceInvoiceSubscriptionsCallService.setPg(currentPageIndex);
		webServiceInvoiceSubscriptionsCallService.setLimit(itemsPerPage);
		webServiceInvoiceSubscriptionsCallService.setId(id);
		webServiceInvoiceSubscriptionsCallService.setSearch(search);
		
		
		if (webServiceInvoiceSubscriptionsCallService.getState() != Service.State.RUNNING) {
			webServiceInvoiceSubscriptionsCallService.start();
		}
	}
	
	private void configureColumns() {
		msisdnColumn.setCellValueFactory(new PropertyValueFactory<>("msisdn"));
		
		iccidColumn.setCellValueFactory(new PropertyValueFactory<>("iccid"));
		
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
		
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("price_plan"));
		
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		trafficColumn.setCellValueFactory(new PropertyValueFactory<>("traffic_kb"));
		
		smsColumn.setCellValueFactory(new PropertyValueFactory<>("number_of_sms"));
		
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
		
		setCenteredCellFactory(msisdnColumn);
		setCenteredCellFactory(iccidColumn);
		setCenteredCellFactory(statusColumn);
		setCenteredCellFactory(descriptionColumn);
		setCenteredCellFactory(totalColumn);
		setCenteredCellFactory(trafficColumn);
		setCenteredCellFactory(smsColumn);
		setCenteredCellFactory(durationColumn);
	}
	
	@FXML
	private void onClickNext(MouseEvent mouseEvent) {
		oldPageIndex = currentPageIndex;
		btnPrevious.setDisable(false);
		btnNext.setDisable(true);
		
		int temp = currentPageIndex + 1;
		if(temp < numberPage) {
			currentPageIndex += 1;
			if(currentPageIndex < numberPage){
				subscriptionsIndicator.setVisible(true);
				listInvoiceLine.setDisable(true);
				startServiceInvoiceSubscription(invoiceSelected.getId(), search);
			}
		}
	}
	
	@FXML
	private void onClickPrevious(MouseEvent mouseEvent) {
		subscriptionsIndicator.setVisible(true);
		listInvoiceLine.setDisable(true);
		oldPageIndex = currentPageIndex;
		if (currentPageIndex > 0) {
			currentPageIndex--;
		}
		
		if(currentPageIndex == 0){
			btnPrevious.setDisable(true);
		}
		startServiceInvoiceSubscription(invoiceSelected.getId(), search);
	}
	@FXML
	private void onClickBack(MouseEvent mouseEvent) {
		Application.setChild(ENameView.INVOICES_VIEW);
		Application.replaceChild(null, invoiceSelected.getUser());
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
	
	@FXML
	public void onClickSearch() {
		isSearch = true;
		subscriptionsIndicator.setVisible(true);
		if(searchField.getText() == null || searchField.getText().equals("")){
			currentPageIndex = 0;
			search = "";
			startServiceInvoiceSubscription(invoiceSelected.getId(), "");
		}
		else {
			currentPageIndex = 0;
			search = searchField.getText();
			startServiceInvoiceSubscription(invoiceSelected.getId(), search);
		}
		
	}
}
