package gui.viewmodel;

import gui.SharepointView;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;

public class LoginViewModel implements ViewModel{

	private StringProperty username = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty address = new SimpleStringProperty();
	private Property<Stage> primaryStage = new SimpleObjectProperty<Stage>();
	
	public StringProperty usernameProperty() {
		return username;
	}
	
	public StringProperty passwordProperty() {
		return password;
	}

	public StringProperty addressProperty() {
		return address;
	}
	
	public Property<Stage> primaryStageProperty() {
		return primaryStage;
	}

	public void login() {
		ViewTuple<SharepointView, SharepointViewModel> viewTuple = FluentViewLoader.fxmlView(SharepointView.class).load();
		
		primaryStage.getValue().setScene(new Scene(viewTuple.getView()));
	}
}
