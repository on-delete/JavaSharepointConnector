package gui;

import java.net.URL;
import java.util.ResourceBundle;

import common.ErrorMessages;
import javafx.fxml.Initializable;
import gui.viewmodel.LoginViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;

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
	
	private IntegerProperty errorMessage = new SimpleIntegerProperty();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
		passwortField.textProperty().bindBidirectional(viewModel.passwordProperty());
		addressField.textProperty().bindBidirectional(viewModel.addressProperty());
		errorMessage.bind(viewModel.errorMessageProperty());
		
		errorMessage.addListener(new ChangeListener<Number>(){
	        @Override public void changed(ObservableValue o,Number oldVal, Number newVal){
	             if((Integer)newVal == ErrorMessages.INTERNAL_ERROR){
	            	 showErrorMessage(ErrorMessages.INTERNAL_ERROR_TEXT);
	             }
	             else if((Integer)newVal == ErrorMessages.NOT_AUTHORIZED){
	            	 showErrorMessage(ErrorMessages.NOT_AUTHORIZED_TEXT);
	             }
	             else if((Integer)newVal == ErrorMessages.NOT_FOUND){
	            	 showErrorMessage(ErrorMessages.NOT_FOUND_TEXT);
	             }
	        }
	      });
	}

	@FXML 
	public void login(ActionEvent event) {
		viewModel.login();
	}
	
	@FXML
	public void close(ActionEvent event){
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

	private void showErrorMessage(String errorMessage){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Fehler");
		alert.setHeaderText("Es ist ein Fehler aufgetreten!");
		alert.setContentText(errorMessage);
		alert.show();
	}
}
