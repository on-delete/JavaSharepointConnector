package gui.sharepointmain;

import gui.model.ListViewItem;
import gui.model.TreeViewListItem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class SharepointMainView implements FxmlView<SharepointMainViewModel>, Initializable {
	
	@InjectViewModel
    private SharepointMainViewModel viewModel;
	
	@FXML
	SplitPane mainSplitPane;
	
	@FXML
	TreeView<TreeViewListItem> structureTree;
	
	@FXML
	ListView<ListViewItem> filesListView;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mainSplitPane.setDividerPositions(0.3f);
		
		viewModel.initTreeViewItems();
		structureTree.setRoot(viewModel.getRootNode());
		structureTree.getRoot().setExpanded(true);
		structureTree.setCellFactory(param -> new StructureTreeViewCell());
		viewModel.selectedTreeItemProperty().bind(structureTree.getSelectionModel().selectedItemProperty());
		
		filesListView.setItems(viewModel.subItemsProperty());
		filesListView.setCellFactory(param -> new FilesListViewCell());
		filesListView.setPlaceholder(new Label("Dieser Ordner ist leer."));
	}
}
