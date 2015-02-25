package gui.sharepointmain;

import gui.model.ContentItem;
import gui.model.NavigationItem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class SharepointMainView implements FxmlView<SharepointMainViewModel>, Initializable {
	
	@InjectViewModel
    private SharepointMainViewModel viewModel;
	
	@FXML
	SplitPane mainSplitPane;
	
	@FXML
	TreeView<NavigationItem> navigation;
	
	@FXML
	ListView<ContentItem> content;
	
	@FXML
	ImageView leftArrowIcon;
	
	@FXML
	ImageView rightArrowIcon;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainSplitPane.setDividerPositions(0.3f);

		navigation.setRoot(viewModel.initRootNode());
		navigation.getRoot().setExpanded(true);
		navigation.setCellFactory(param -> new NavigationItemCell());
		viewModel.selectedNaviagtionItemProperty().bind(navigation.getSelectionModel().selectedItemProperty());
		
		content.setItems(viewModel.contentItemListProperty());
		content.setCellFactory(param -> new ContentItemCell());
		content.setPlaceholder(new Label("Dieser Ordner ist leer."));
	}
}
