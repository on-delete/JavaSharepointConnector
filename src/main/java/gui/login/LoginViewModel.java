package gui.login;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.inject.Inject;

import common.Constants;

import service.SharepointService;
import service.util.UserCredentials;
import de.saxsys.mvvmfx.ViewModel;

public class LoginViewModel implements ViewModel{

	private StringProperty username = new SimpleStringProperty(LoginViewMockData.username);
	private StringProperty password = new SimpleStringProperty(LoginViewMockData.password);
	private StringProperty address = new SimpleStringProperty(LoginViewMockData.url);
	private IntegerProperty errorMessage = new SimpleIntegerProperty(0);
	private IntegerProperty viewNumber = new SimpleIntegerProperty(1);
	private StringProperty progressStatus = new SimpleStringProperty("");
	private ObjectProperty<LoginService> loginWorker = new SimpleObjectProperty<LoginService>();
	
	private UserCredentials userCredentials;
	private SharepointService service;
	private LoginService loginService;
	
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

	public IntegerProperty viewNumberProperty() {
		return viewNumber;
	}

	public StringProperty progressStatusProperty() {
		return progressStatus;
	}

	public ObjectProperty<LoginService> loginWorkerProperty() {
		return loginWorker;
	}
	
	public void initSharepointService(){
		userCredentials = new UserCredentials(username.getValue() , password.getValue());
		service.initialize(address.getValue(), userCredentials);
	}
	
	public void initLoginService(){
		loginService = new LoginService(service, this);
		loginWorker.set(loginService);
	}

	public void login() {
		loginService.start();
	}
	
	public void loginSuccess(){
		viewNumber.set(Constants.SHAREPOINT_VIEW);
	}
}
