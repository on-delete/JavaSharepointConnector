package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import gui.viewmodel.LoginViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class LoginView implements FxmlView<LoginViewModel>, Initializable {

	@FXML
	Parent root;
	
	@InjectViewModel
    private LoginViewModel viewModel;
	
	@FXML 
	TextField usernameField;
	
	@FXML 
	PasswordField passwortField;
	
	@FXML 
	TextField addressField;
	
	@FXML 
	Button loginButton;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usernameField.textProperty().bindBidirectional(viewModel.usernameProperty());
		passwortField.textProperty().bindBidirectional(viewModel.passwordProperty());
		addressField.textProperty().bindBidirectional(viewModel.addressProperty());
	}

	@FXML 
	public void login(ActionEvent event) {
		Stage stage = (Stage) root.getScene().getWindow();
		viewModel.primaryStageProperty().setValue(stage);
		viewModel.login();
	}
	
	@FXML
	public void close(ActionEvent event){
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}

}
