package gui;

import gui.viewmodel.LoginViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ApplicationStarter extends Application{

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Hello World Application");

	    ViewTuple<LoginView, LoginViewModel> viewTuple = FluentViewLoader.fxmlView(LoginView.class).load();

	    Parent root = viewTuple.getView();
	    primaryStage.setScene(new Scene(root));
	    primaryStage.setResizable(false);
	    primaryStage.initStyle(StageStyle.UNDECORATED);
	    primaryStage.show();
	}

}
