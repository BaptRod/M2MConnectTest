package be.anb.rimex.m2mconnect.view.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subscriptions {
	
	private final  StringProperty nbrTotal =  new SimpleStringProperty();
	private final StringProperty nbrActivate =  new SimpleStringProperty();
	private final StringProperty nbrBreak =  new SimpleStringProperty();
	private final StringProperty nbrDesactivate =  new SimpleStringProperty();
	private final StringProperty numPage =  new SimpleStringProperty();
	
	public String getSearch() {
		if(search.get() == null || search.get() == ""){
			return "";
		}
		return search.get();
	}
	
	public StringProperty searchProperty() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search.set(search);
	}
	
	private final StringProperty search = new SimpleStringProperty();
	
	
	public String getNumPage() {
		return numPage.get();
	}
	
	public StringProperty numPageProperty() {
		return numPage;
	}
	
	public void setNumPage(String np) {
		numPage.set(np);
	}
	
	public String getNbrTotal() {
		return nbrTotal.get();
	}
	
	public StringProperty nbrTotalProperty() {
		return nbrTotal;
	}
	
	public void setNbrTotal(String nbrTotal) {
		this.nbrTotal.set(nbrTotal);
	}
	
	public String getNbrActivate() {
		return nbrActivate.get();
	}
	
	public StringProperty nbrActivateProperty() {
		return nbrActivate;
	}
	
	public void setNbrActivate(String nbrActivate) {
		this.nbrActivate.set(nbrActivate);
	}
	
	public String getNbrBreak() {
		return nbrBreak.get();
	}
	
	public StringProperty nbrBreakProperty() {
		return nbrBreak;
	}
	
	public void setNbrBreak(String nbrBreak) {
		this.nbrBreak.set(nbrBreak);
	}
	
	public String getNbrDesactivate() {
		return nbrDesactivate.get();
	}
	
	public StringProperty nbrDesactivateProperty() {
		return nbrDesactivate;
	}
	
	public void setNbrDesactivate(String nbrDesactivate) {
		this.nbrDesactivate.set(nbrDesactivate);
	}
}
