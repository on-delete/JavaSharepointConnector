package gui.login;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import service.SharepointService;

import common.Constants;

public class LoginService extends Service<Void>{

	private SharepointService service;
	private LoginViewModel loginViewModel;
	
	public LoginService(SharepointService service, LoginViewModel loginViewModel){
		this.service = service;
		this.loginViewModel = loginViewModel;
	}

	@Override
	protected Task<Void> createTask() {
		return new Task<Void>() {
			
			@Override
			protected Void call() throws Exception {
				changeLabelLater("Teste Sharepoint-Verbindung...");
				updateProgress(1, 3);

				if(!testConnection()){
					return null;
				}

				changeLabelLater("Empfange Ordner und Dateien...");
				updateProgress(2, 3);
					
				service.getSharepointFiles();
					
				changeLabelLater("Fertig...");
				updateProgress(3, 3);

				changeNewViewLater();
				
				return null;
			}
		};
	}
	
	@Override
	protected void succeeded() {
		changeLabelLater("");
	    reset();
	}
	
	private boolean testConnection() {
		loginViewModel.errorMessageProperty().set(0);
		int errorMessageInput = service.testSharepointConnection();

		if (errorMessageInput == Constants.SUCCESS) {
			return true;
		} else {
			if (errorMessageInput == Constants.NOT_AUTHORIZED) {
				loginViewModel.errorMessageProperty().set(Constants.NOT_AUTHORIZED);
			}
			else if (errorMessageInput == Constants.NOT_FOUND) {
				loginViewModel.errorMessageProperty().set(Constants.NOT_FOUND);
			} 
			else if (errorMessageInput == Constants.INTERNAL_ERROR) {
				loginViewModel.errorMessageProperty().set(Constants.INTERNAL_ERROR);
			} 
			return false;
		}
	}
	
	private void changeLabelLater(String text){
		Platform.runLater(() -> loginViewModel.progressStatusProperty().set(text));
	}
	
	private void changeNewViewLater(){
		Platform.runLater(() -> loginViewModel.loginSuccess());
	}
}
