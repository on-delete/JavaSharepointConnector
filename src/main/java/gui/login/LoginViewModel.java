package gui.login;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;

import service.SharepointService;
import service.util.UserCredentials;

import common.ErrorMessages;

import de.saxsys.mvvmfx.ViewModel;

public class LoginViewModel implements ViewModel{

	private StringProperty username = new SimpleStringProperty(LoginViewMockData.username);
	private StringProperty password = new SimpleStringProperty(LoginViewMockData.password);
	private StringProperty address = new SimpleStringProperty(LoginViewMockData.url);
	private IntegerProperty errorMessage = new SimpleIntegerProperty(0);
	private BooleanProperty isActive = new SimpleBooleanProperty(true);
	
	private UserCredentials userCredentials;
	private SharepointService service;
	
	@Inject
	public LoginViewModel(SharepointService service){
		this.service = service;
	}
	
	public StringProperty usernameProperty() {
		return username;
	}
	
	public StringProperty passwordProperty() {
		return password;
	}

	public StringProperty addressProperty() {
		return address;
	}
	
	public IntegerProperty errorMessageProperty() {
		return errorMessage;
	}

	public BooleanProperty isActiveProperty() {
		return isActive;
	}

	public void login() {
		userCredentials = new UserCredentials(username.getValue() , password.getValue());
		
		service.initialize(address.getValue(), userCredentials);
		
		if(testConnection()){
			isActive.set(false);
		}
	}
	
	private boolean testConnection(){
		errorMessage.set(0);
		int errorMessage = service.testSharepointConnection();
		
		switch(errorMessage){
			case ErrorMessages.SUCCESS:{
				return true;
			}
			case ErrorMessages.NOT_AUTHORIZED:{
				this.errorMessage.set(ErrorMessages.NOT_AUTHORIZED);
				return false;
			}
			case ErrorMessages.NOT_FOUND:{
				this.errorMessage.set(ErrorMessages.NOT_FOUND);
				return false;
			}
			case ErrorMessages.INTERNAL_ERROR:{
				this.errorMessage.set(ErrorMessages.INTERNAL_ERROR);
				return false;
			}
			default:{
				return false;
			}
		}
	}
}
