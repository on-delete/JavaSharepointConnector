package gui;

import de.saxsys.mvvmfx.cdi.MvvmfxCdiApplication;
import javafx.stage.Stage;

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