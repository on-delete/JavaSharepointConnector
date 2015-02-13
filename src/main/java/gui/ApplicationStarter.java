package gui;

import javafx.stage.Stage;
import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;

public class ApplicationStarter extends MvvmfxCdiApplication{

	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void startMvvmfx(Stage primaryStage) throws Exception {
		ViewController viewController = new ViewController();
		viewController.initStage(primaryStage);
	}
}