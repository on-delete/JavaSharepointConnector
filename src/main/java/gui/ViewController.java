package gui;

import common.Constants;

import gui.login.LoginView;
import gui.login.LoginViewModel;
import gui.sharepointmain.SharepointMainView;
import gui.sharepointmain.SharepointMainViewModel;
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

		if(Constants.WITHOUT_LOGIN){
			ViewTuple<SharepointMainView, SharepointMainViewModel> viewTuple = FluentViewLoader.fxmlView(SharepointMainView.class).load();
			
			Parent root = viewTuple.getView();
		    this.primaryStage.setScene(new Scene(root));
		    this.primaryStage.setResizable(false);
		    //this.primaryStage.initStyle(StageStyle.UTILITY);
		    this.primaryStage.show();
		}
		else{
			ViewTuple<LoginView, LoginViewModel> viewTuple = FluentViewLoader.fxmlView(LoginView.class).load();
		    LoginViewModel loginViewModel = viewTuple.getViewModel();
		    
		    Parent root = viewTuple.getView();
		    this.primaryStage.setScene(new Scene(root));
		    this.primaryStage.setResizable(false);
		    //this.primaryStage.initStyle(StageStyle.UTILITY);
		    this.primaryStage.show();
		    
		    setChangeListenerForView(loginViewModel);
		}
	}
	
	private void setChangeListenerForView(LoginViewModel loginViewModel){
		loginViewModel.isActiveProperty().addListener(new ChangeListener<Boolean>(){
	        @Override public void changed(ObservableValue o,Boolean oldVal, Boolean newVal){
	             if(!newVal){
	            	ViewTuple<SharepointMainView, SharepointMainViewModel> sharepointViewTuple = FluentViewLoader.fxmlView(SharepointMainView.class).load();
	     			
	     			primaryStage.setScene(new Scene(sharepointViewTuple.getView()));
	             }
	        }
	      });
	}
}
