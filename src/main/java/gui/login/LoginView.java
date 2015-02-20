package gui.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import common.Constants;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class LoginView implements FxmlView<LoginViewModel>, Initializable {

	@FXML
	private Parent root;
	
	@InjectViewModel
    private LoginViewModel viewModel;
	
	@FXML 
	private TextField usernameField;
	
	@FXML 
	private PasswordField passwortField;
	
	@FXML 
	private TextField addressField;
	
	@FXML 
	private Button loginButton;
	
	@FXML 
	private Button closeButton;
	
	@FXML
	private Label progressBarLabel;
	
	@FXML
	private ProgressBar progressBar;
	
	private IntegerProperty errorMessage = new SimpleIntegerProperty();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
		passwortField.textProperty().bindBidirectional(viewModel.passwordProperty());
		addressField.textProperty().bindBidirectional(viewModel.addressProperty());
		progressBarLabel.textProperty().bind(viewModel.progressStatusProperty());
		progressBar.setProgress(0);
		errorMessage.bind(viewModel.errorMessageProperty());
		
		errorMessage.addListener((ChangeListener<Number>) (o, oldVal, newVal) -> {
		     if((Integer)newVal == Constants.INTERNAL_ERROR){
		    	 showErrorMessage(Constants.INTERNAL_ERROR_TEXT);
		     }
		     else if((Integer)newVal == Constants.NOT_AUTHORIZED){
		    	 showErrorMessage(Constants.NOT_AUTHORIZED_TEXT);
		     }
		     else if((Integer)newVal == Constants.NOT_FOUND){
		    	 showErrorMessage(Constants.NOT_FOUND_TEXT);
		     }
		});
	}

	@FXML 
	public void login(ActionEvent event) {
		viewModel.initSharepointService();
		viewModel.initLoginService();
		progressBar.progressProperty().bind(viewModel.loginWorkerProperty().get().progressProperty());
		viewModel.login();

	}
	
	@FXML
	public void close(ActionEvent event){
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

	private void showErrorMessage(String errorMessage){
		Platform.runLater(() -> {
			progressBar.progressProperty().unbind();
			progressBar.setProgress(0);
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Fehler");
			alert.setHeaderText("Es ist ein Fehler aufgetreten!");
			alert.setContentText(errorMessage);
			alert.show();
		});
	}
}
