package gui;

import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;
import javafx.stage.Stage;

public class ApplicationStarter extends MvvmfxGuiceApplication{

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void startMvvmfx(Stage primaryStage) throws Exception {
		ViewController viewController = new ViewController();
		viewController.initStage(primaryStage);
	}
}