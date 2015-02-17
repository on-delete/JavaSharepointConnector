package gui.login;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

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
	private StringProperty progressStatus = new SimpleStringProperty("");
	private ObjectProperty<LoginService> loginWorker = new SimpleObjectProperty<LoginService>(new LoginService());
	
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

	public StringProperty progressStatusProperty() {
		return progressStatus;
	}

	public ObjectProperty<LoginService> loginWorkerProperty() {
		return loginWorker;
	}

	public void login() {
		loginWorker.get().start();
	}
	
	private boolean testConnection() {

		errorMessage.set(0);
		int errorMessageInput = service.testSharepointConnection();

		switch (errorMessageInput) {
			case ErrorMessages.SUCCESS: {
				 return true;
			}
			case ErrorMessages.NOT_AUTHORIZED: {
				errorMessage.set(ErrorMessages.NOT_AUTHORIZED);
				 return false;
			}
			case ErrorMessages.NOT_FOUND: {
				errorMessage.set(ErrorMessages.NOT_FOUND);
				return false;
			}
			case ErrorMessages.INTERNAL_ERROR: {
				errorMessage.set(ErrorMessages.INTERNAL_ERROR);
				return false;
			}
			default: {
				return false;
			}
		}
	}
	
	class LoginService extends Service<Boolean>{
		@Override
		protected Task<Boolean> createTask(){
			return new Task<Boolean>() {
	
				@Override
				protected Boolean call() throws Exception {
					changeLabelLater("Teste Sharepoint-Verbindung...");
	
					updateProgress(1, 3);
					userCredentials = new UserCredentials(username.getValue() , password.getValue());
					
					service.initialize(address.getValue(), userCredentials);
					
					if(!testConnection()){
						return true;
					}
	
					changeLabelLater("Empfange Ordner und Dateien...");
	
					updateProgress(2, 3);
						
					service.getSharepointFiles();
						
					changeLabelLater("Fertig...");
	
					updateProgress(3, 3);
	
					changeNewViewLater();
					
					return true;
				}
			};
		}
		
		@Override
		protected void succeeded() {
			changeLabelLater("");
		    reset();
		}
	}

	private void changeLabelLater(String text){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				progressStatus.set(text);
			}
		});
	}
	
	private void changeNewViewLater(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				isActive.set(false);
			}
		});
	}
}
