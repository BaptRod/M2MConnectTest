package be.anb.rimex.m2mconnect.view;

import be.anb.rimex.m2mconnect.common.EProperty;
import be.anb.rimex.m2mconnect.common.ResourceLanguage;
import be.anb.rimex.m2mconnect.service.exception.EM2MError;
import be.anb.rimex.m2mconnect.service.exception.M2MSubscriptionsException;
import be.anb.rimex.m2mconnect.service.response.SubscriptionPackPriceResponse;
import be.anb.rimex.m2mconnect.view.entity.PricePack;
import be.anb.rimex.m2mconnect.view.entity.User;
import be.anb.rimex.m2mconnect.view.service.WebServiceSubscriptionsPricesCallService;
import be.anb.rimex.m2mconnect.view.util.Translate;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class SubscriptionsPricesController implements IParameter{
	
	@FXML
	private ProgressIndicator progressIndicator;
	
	@FXML
	private TableView<PricePack> pricesList;
	
	@FXML
	private TableColumn<PricePack, String> packColumn;
	
	@FXML
	private TableColumn<PricePack, String> priceColumn;
	
	private WebServiceSubscriptionsPricesCallService webServiceSubscriptionsPricesCallService;
	private User u;
	private ObservableList<PricePack> data;
	public void initialize() {
		Application.setChild(ENameView.SUBSCRIPTIONS_PRICES);
		pricesList.setPlaceholder(new Label(""));
		data = FXCollections.observableArrayList();
		pricesList.setDisable(true);
		progressIndicator.setVisible(true);
		configureColumns();
		
	}
	@Override
	public void setParameter(Object o) {
		
		u = (User) o;
		initialiseServicePrices();
		startServicePrices();
	}
	
	private void initialiseServicePrices() {
		
		webServiceSubscriptionsPricesCallService = new WebServiceSubscriptionsPricesCallService();
		onSuccessPricesSubscriptions();
		onFaillurePricesSubscriptions();
		
	}
	
	private void onSuccessPricesSubscriptions() {
		webServiceSubscriptionsPricesCallService.setOnSucceeded(e -> {
			List<SubscriptionPackPriceResponse> result = webServiceSubscriptionsPricesCallService.getValue();
			for(SubscriptionPackPriceResponse r: result){
				if(r.getPrice() != 0.00) {
					data.add(new PricePack(Translate.getTranslateForPack(r.getPack()), Float.toString(r.getPrice()) + "€"));
				}
			}
			updateTableView();
			pricesList.setDisable(false);
			progressIndicator.setVisible(false);
			
			
		});
	}
	
	private void updateTableView() {
		Platform.runLater(() -> {
			pricesList.setItems(FXCollections.observableArrayList(data));
			pricesList.refresh();
		});
		
	}
	
	private void onFaillurePricesSubscriptions() {
		webServiceSubscriptionsPricesCallService .setOnFailed(e -> {
			M2MSubscriptionsException exception = (M2MSubscriptionsException) webServiceSubscriptionsPricesCallService .getException();
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
	
	
	private void startServicePrices() {
		if (webServiceSubscriptionsPricesCallService.getState() == Service.State.FAILED
			|| webServiceSubscriptionsPricesCallService.getState() == Service.State.CANCELLED
			|| webServiceSubscriptionsPricesCallService.getState() == State.SUCCEEDED) {
			webServiceSubscriptionsPricesCallService.reset();
		}
		webServiceSubscriptionsPricesCallService.setIdUser(u.getIdUser());
		
		if (webServiceSubscriptionsPricesCallService.getState() != Service.State.RUNNING) {
			webServiceSubscriptionsPricesCallService.start();
		}
	}
	
	
	
	private void configureColumns() {
		packColumn.setCellValueFactory(new PropertyValueFactory<>("pack"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		setCenteredCellFactory(packColumn);
		setCenteredCellFactory(priceColumn);
		
	}
	
	
	
	@FXML
	private void onClickBack(MouseEvent event) {
		Application.setChild(ENameView.SUBSCRIPTIONS_VIEW);
		Application.replaceChild(null, u);
	}
	
	private void setCenteredCellFactory(TableColumn<PricePack, String> column) {
		column.setCellFactory(new Callback<TableColumn<PricePack, String>, TableCell<PricePack, String>>() {
			@Override
			public TableCell<PricePack, String> call(TableColumn<PricePack, String> param) {
				return new TableCell<PricePack, String>() {
					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText(null);
						} else {
							setText(item);
							setAlignment(Pos.CENTER);
						}
					}
				};
			}
		});
	}
	
	
	
}
