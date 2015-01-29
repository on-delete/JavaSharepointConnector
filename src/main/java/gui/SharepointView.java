package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import gui.viewmodel.SharepointViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class SharepointView implements FxmlView<SharepointViewModel>, Initializable {

	@InjectViewModel
    private SharepointViewModel viewModel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
