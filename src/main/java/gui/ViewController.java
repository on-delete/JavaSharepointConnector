package gui;

import gui.view.LoginView;
import gui.view.SharepointView;
import gui.viewmodel.LoginViewModel;
import gui.viewmodel.SharepointViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;

public class ViewController {

	private Stage primaryStage;
	
	public void initStage(Stage primaryStage){
		this.primaryStage = primaryStage;
		
		this.primaryStage.setTitle("JavaSharepointConnector");

	    ViewTuple<LoginView, LoginViewModel> loginViewTuple = FluentViewLoader.fxmlView(LoginView.class).load();
	    LoginViewModel loginViewModel = loginViewTuple.getViewModel();
	    
	    Parent root = loginViewTuple.getView();
	    this.primaryStage.setScene(new Scene(root));
	    this.primaryStage.setResizable(false);
	    this.primaryStage.initStyle(StageStyle.UTILITY);
	    this.primaryStage.show();
	    
	    setChangeListenerForView(loginViewModel);
	}
	
	private void setChangeListenerForView(LoginViewModel loginViewModel){
		loginViewModel.isActiveProperty().addListener(new ChangeListener<Boolean>(){
	        @Override public void changed(ObservableValue o,Boolean oldVal, Boolean newVal){
	             if(!newVal){
	            	ViewTuple<SharepointView, SharepointViewModel> sharepointViewTuple = FluentViewLoader.fxmlView(SharepointView.class).load();
	     			
	     			primaryStage.setScene(new Scene(sharepointViewTuple.getView()));
	             }
	        }
	      });
	}
}
