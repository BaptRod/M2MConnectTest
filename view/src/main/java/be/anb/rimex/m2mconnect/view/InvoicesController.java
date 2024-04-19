package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.ExcelExporter;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.InvoiceDetailsResponse;
import be.anb.rimex.m2mconnect.service.response.InvoiceLineResponse;
import be.anb.rimex.m2mconnect.service.response.InvoicesResponse;
import be.anb.rimex.m2mconnect.service.response.SubscriptionHistoryResponse;
import be.anb.rimex.m2mconnect.view.entity.History;
import be.anb.rimex.m2mconnect.view.entity.Invoice;
import be.anb.rimex.m2mconnect.view.entity.Subscription;
import be.anb.rimex.m2mconnect.view.entity.Subscriptions;
import be.anb.rimex.m2mconnect.view.entity.User;
import be.anb.rimex.m2mconnect.view.service.WebServiceInvoiceSubscriptionsCallService;
import be.anb.rimex.m2mconnect.view.service.WebServiceInvoicesCallService;
import be.anb.rimex.m2mconnect.view.service.WebServiceSubscriptionDetailsCallService;
import be.anb.rimex.m2mconnect.view.util.DateConverter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class InvoicesController implements IParameter{
	
	
	@FXML
	private TextField searchField;
	@FXML
	private ImageView searchBtn;
	@FXML
	private ProgressIndicator progressIndicator;
	@FXML
	private TableView<Invoice> invoicesList;
	
	@FXML
	private TableColumn<Invoice, String> referenceColumn;
	@FXML
	private TableColumn<Invoice, String> clientColumn;
	@FXML
	private TableColumn<Invoice, String> idColumn;
	@FXML
	private TableColumn<Invoice, String> totalColumn;
	@FXML
	private TableColumn<Invoice, String> startColumn;
	@FXML
	private TableColumn<Invoice, String> endColumn;
	@FXML
	private TableColumn<Invoice, Void> actionColumn;
	
	private ObservableList<Invoice> data;
	
	private FilteredList<Invoice> filteredData;
	
	private WebServiceInvoicesCallService webServiceInvoicesCallService;
	
	private WebServiceInvoiceSubscriptionsCallService webServiceInvoiceSubscriptionsCallService;
	private User u;
	
	private Invoice invoice;
	
	public void initialize() {
		Application.setChild(ENameView.INVOICES_VIEW);
		searchField.setFocusTraversable(false);
		invoicesList.setPlaceholder(new Label(""));
		data = FXCollections.observableArrayList();
		invoicesList.setDisable(true);
		progressIndicator.setVisible(true);
		configureColumns();
		
		
	}
	
	@Override
	public void setParameter(Object o){
		u = (User) o;
		initialiseServiceInvoices();
		initialiseServiceInvoiceSubscriptions();
		startServiceInvoices();
	}
	
	private void searchListener(){
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(invoice -> {
				// Si le texte est vide, afficher toutes les factures
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Comparer le texte filtré avec la référence ou l'ID de la facture
				String lowerCaseFilter = newValue.toLowerCase();
				return invoice.getReference().toLowerCase().contains(lowerCaseFilter) ||
					invoice.getIdInString().toLowerCase().contains(lowerCaseFilter);
			});
			searchListener();
		});
		
		SortedList<Invoice> sortedList = new SortedList<>(filteredData);
		sortedList.comparatorProperty().bind(invoicesList.comparatorProperty());
		invoicesList.setItems(sortedList);
		
	}
	
	private void initialiseServiceInvoiceSubscriptions() {
		webServiceInvoiceSubscriptionsCallService = new WebServiceInvoiceSubscriptionsCallService();
		onSuccessInvoiceSubscriptions();
		onFaillureInvoiceSubscriptions();
	}
	
	private void onFaillureInvoiceSubscriptions() {
		webServiceInvoiceSubscriptionsCallService .setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceInvoiceSubscriptionsCallService .getException();
			errorBusiness(exception, ENameView.INVOICES_VIEW);
		});
	}
	
	private void onSuccessInvoiceSubscriptions() {
		webServiceInvoiceSubscriptionsCallService.setOnSucceeded(e -> {
			FileChooser fileChooser = new FileChooser();
			InvoiceDetailsResponse result = webServiceInvoiceSubscriptionsCallService.getValue();
			List<String[]> allData = new ArrayList<>();
			String[] d = {"PHONE NBR", "SIM CARD ICCID", "STATE", "PRICEPLAN", "PRICE", "TRAFFIC IN KB", "NUMBER OF SMS", "DURATION (SECONDS)"};
			allData.add(d);
			for (InvoiceLineResponse i : result.getInvoiceLineList()){
				String[] data = {i.getMsisdn(), i.getIccid(), i.getState(), i.getPrice_plan(), String.valueOf(i.getTotal()), String.valueOf(i.getTraffic_kb()),
					String.valueOf(i.getNumber_of_sms()), String.valueOf(i.getDuration())};
				allData.add(data);
			}
			try {
				fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
				fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + File.separator + "Downloads"));
				File selectedFile = fileChooser.showSaveDialog(Application.getStage());
				if(selectedFile != null) {
					ExcelExporter.exportToExcel(allData, selectedFile);
					Application.showConfirm(
						ResourceLanguage.getInstance().getTranslation(EProperty.INVOICES_ACTION_BUTTON_SUCCESS.getName()));
				}
			}catch (IOException exception){
				Application.showErrorAlert(ResourceLanguage.getInstance().getTranslation(EProperty.INVOICES_ACTION_BUTTON_ERROR.getName()));
			}
		});
	}
	
	private void startServiceInvoices() {
		if (webServiceInvoicesCallService.getState() == Service.State.FAILED
			|| webServiceInvoicesCallService.getState() == Service.State.CANCELLED
			|| webServiceInvoicesCallService.getState() == State.SUCCEEDED) {
			webServiceInvoicesCallService.reset();
		}
		webServiceInvoicesCallService.setIdUser(u.getIdUser());
		
		if (webServiceInvoicesCallService.getState() != Service.State.RUNNING) {
			webServiceInvoicesCallService.start();
		}
	}
	
	private void initialiseServiceInvoices() {
		webServiceInvoicesCallService = new WebServiceInvoicesCallService();
		onSuccessInvoices();
		onFaillureInvoices();
	}
	
	private void onSuccessInvoices() {
		webServiceInvoicesCallService.setOnSucceeded(e -> {
			List<InvoicesResponse> result = webServiceInvoicesCallService.getValue();
			for(InvoicesResponse invoicesResponse : result){
				data.add(new Invoice(invoicesResponse.getReference(), u.getName(), invoicesResponse.getAccounting_invoice().getName(),  Float.toString(invoicesResponse.getTotal()) + "€", invoicesResponse.getAccounting_invoice().getId(),
					DateConverter.dateToString(invoicesResponse.getStart_period(), "dd-MM-yyyy"), DateConverter.dateToString(invoicesResponse.getEnd_period(), "dd-MM-yyyy")));
			}
			filteredData = new FilteredList<>(data, p -> true);
			searchListener();
			updateTableView();
			invoicesList.setDisable(false);
			progressIndicator.setVisible(false);
			
		});
	}
	
	private void onFaillureInvoices(){
		webServiceInvoicesCallService.setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceInvoicesCallService.getException();
			progressIndicator.setVisible(false);
			errorBusiness(exception, ENameView.SUBSCRIPTIONS_VIEW);
		});
	}
	private void errorBusiness(M2MSubscriptionsException exception, ENameView eNameView){
		if(exception.getCode() == EM2MError.SERVER_ERROR) {
			Application.showErrorAlert(ResourceLanguage.getInstance().getTranslation(EProperty.SERVER_ERROR.getName()));
			Application.setChild(eNameView);
			Application.replaceChild(null, u);
		}
	}
	
	private void configureColumns() {
		referenceColumn.setCellValueFactory(new PropertyValueFactory<>("reference"));
		
		clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("idInString"));
		
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		
		startColumn.setCellValueFactory(new PropertyValueFactory<>("start"));
		
		endColumn.setCellValueFactory(new PropertyValueFactory<>("end"));
		
		actionColumn.setCellFactory(column -> new ActionButtonCell());
		
		
		setCenteredCellFactory(referenceColumn);
		setCenteredCellFactory(clientColumn);
		setCenteredCellFactory(idColumn);
		setCenteredCellFactory(totalColumn);
		setCenteredCellFactory(startColumn);
		setCenteredCellFactory(endColumn);
		
		eventOnColumn();
	}
	
	private void eventOnColumn(){
		invoicesList.setRowFactory(tv -> {
			TableRow<Invoice> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && !row.isEmpty()) {
					Invoice selectedInvoice = row.getItem();
					selectedInvoice.setUser(u);
					Application.replaceChild(ENameView.INVOICE_DETAILS_VIEW, selectedInvoice);
				}
			});
			return row;
		});
	}
	
	@FXML
	private void onClickBack(MouseEvent event) {
		Application.setChild(ENameView.SUBSCRIPTIONS_VIEW);
		Application.replaceChild(null, u);
	}
	
	private void updateTableView() {
		Platform.runLater(() -> {
			invoicesList.setItems(FXCollections.observableArrayList(data));
			invoicesList.refresh();
		});
		
	}
	
	private void setCenteredCellFactory(TableColumn<Invoice, String> column) {
		column.setCellFactory(new Callback<TableColumn<Invoice, String>, TableCell<Invoice, String>>() {
			@Override
			public TableCell<Invoice, String> call(TableColumn<Invoice, String> param) {
				return new TableCell<Invoice, String>() {
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
	
	private void startServiceInvoiceSubscription(int id) {
		if (webServiceInvoiceSubscriptionsCallService.getState() == Service.State.FAILED
			|| webServiceInvoiceSubscriptionsCallService.getState() == Service.State.CANCELLED
			|| webServiceInvoiceSubscriptionsCallService.getState() == State.SUCCEEDED) {
			webServiceInvoiceSubscriptionsCallService.reset();
		}
		webServiceInvoiceSubscriptionsCallService.setId(id);
		
		if (webServiceInvoiceSubscriptionsCallService.getState() != Service.State.RUNNING) {
			webServiceInvoiceSubscriptionsCallService.start();
		}
	}
	
	public class ActionButtonCell extends TableCell<Invoice, Void> {
		private final Button actionButton;
		public ActionButtonCell() {
			actionButton = new Button(ResourceLanguage.getInstance().getTranslation(EProperty.INVOICES_ACTION_BUTTON.getName()));
			actionButton.setOnAction(event -> {
				invoice = getTableView().getItems().get(getIndex());
				startServiceInvoiceSubscription(invoice.getId());
			});
			setGraphic(actionButton);
		}
		
		@Override
		protected void updateItem(Void item, boolean empty) {
			super.updateItem(item, empty);
			if (empty) {
				setGraphic(null);
			} else {
				setGraphic(actionButton);
				setAlignment(Pos.CENTER);
				setPadding(new Insets(5));
			}
		}
	}
	
}
