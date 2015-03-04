package gui.sharepointmain;

import gui.model.ContentItem;
import gui.model.NavigationItem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class SharepointMainView implements FxmlView<SharepointMainViewModel>, Initializable {
	
	@InjectViewModel
    private SharepointMainViewModel viewModel;
	
	@FXML
	private Parent root;
	
	@FXML
	private SplitPane mainSplitPane;
	
	@FXML
	private TreeView<NavigationItem> navigation;
	
	@FXML
	private ListView<ContentItem> content;
	
	@FXML
	private ImageView leftArrowIcon;
	
	@FXML
	private ImageView rightArrowIcon;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainSplitPane.setDividerPositions(0.3f);

		navigation.setRoot(viewModel.initRootNode());
		navigation.getRoot().setExpanded(true);
		navigation.setCellFactory(param -> new NavigationItemCell());
		viewModel.selectedNaviagtionItemProperty().bind(navigation.getSelectionModel().selectedItemProperty());
		viewModel.navigationItemHistoryProperty().addListener((ChangeListener<TreeItem<NavigationItem>>) (observable, oldValue, newValue) -> {
			navigation.getSelectionModel().select(newValue);
		});
		
		
		content.setItems(viewModel.contentItemListProperty());
		content.setCellFactory(param -> new ContentItemCell(viewModel));
		content.setPlaceholder(new Label("Dieser Ordner ist leer."));

		leftArrowIcon.disableProperty().bind(Bindings.not(viewModel.undoAvailableProperty()));
		leftArrowIcon.setOnMouseClicked(evt -> viewModel.undo());
		leftArrowIcon.disableProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					leftArrowIcon.getStyleClass().clear();
					leftArrowIcon.getStyleClass().add("imageView_left_disabled");
				}
				else{
					leftArrowIcon.getStyleClass().clear();
					leftArrowIcon.getStyleClass().add("imageView_left");
				}
			}
		});
		rightArrowIcon.disableProperty().bind(Bindings.not(viewModel.redoAvailableProperty()));
		rightArrowIcon.setOnMouseClicked(evt -> viewModel.redo());
		rightArrowIcon.disableProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				if(newValue){
					rightArrowIcon.getStyleClass().clear();
					rightArrowIcon.getStyleClass().add("imageView_right_disabled");
				}
				else{
					rightArrowIcon.getStyleClass().clear();
					rightArrowIcon.getStyleClass().add("imageView_right");
				}
			}
		});
	}
	
	@FXML
	public void closeSharepointMainView(ActionEvent event){
		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
	}
}
