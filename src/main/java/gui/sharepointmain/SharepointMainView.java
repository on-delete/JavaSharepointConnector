package gui.sharepointmain;

import gui.model.ContentItem;
import gui.model.NavigationItem;

import java.net.URL;
import java.util.ResourceBundle;

import org.fxmisc.undo.UndoManager;
import org.fxmisc.undo.UndoManagerFactory;
import org.reactfx.Change;
import org.reactfx.EventStream;

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
import static org.reactfx.EventStreams.*;

public class SharepointMainView implements FxmlView<SharepointMainViewModel>, Initializable {
	
	private UndoManager undoManager;
	
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
		
		content.setItems(viewModel.contentItemListProperty());
		content.setCellFactory(param -> new ContentItemCell(viewModel));
		content.setPlaceholder(new Label("Dieser Ordner ist leer."));
		
		EventStream<Change<TreeItem<NavigationItem>>> selectedItemChanges =
		        changesOf(navigation.getSelectionModel().selectedItemProperty());

		undoManager = UndoManagerFactory.unlimitedHistoryUndoManager(
		        selectedItemChanges, // stream of changes to observe
				c -> navigation.getSelectionModel().select(c.getNewValue()),  // function to redo a change
				c -> navigation.getSelectionModel().select(c.getOldValue())); // function to undo a change
		
		leftArrowIcon.disableProperty().bind(Bindings.not(undoManager.undoAvailableProperty()));
		leftArrowIcon.setOnMouseClicked(evt -> undoManager.undo());
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
		rightArrowIcon.disableProperty().bind(Bindings.not(undoManager.redoAvailableProperty()));
		rightArrowIcon.setOnMouseClicked(evt -> undoManager.redo());
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
