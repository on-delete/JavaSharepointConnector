package gui.sharepointmain;

import gui.model.ListViewItem;
import gui.model.TreeViewListItem;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;

public class SharepointMainView implements FxmlView<SharepointMainViewModel>, Initializable {

	private boolean isMenuOpen = false;
	private Image menuDown = new Image("images/triangle_down.png");
	private Image menuUp = new Image("images/triangle_up.png");
	
	@InjectViewModel
    private SharepointMainViewModel viewModel;
	
	@FXML
	Pane menuPane;
	
	@FXML 
	HBox iconBox;
	
	@FXML
	Pane triggerPane;
	
	@FXML
	ImageView menuTriggerImage;
	
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
		
		viewModel.selectedTreeItemProperty().bind(structureTree.getSelectionModel().selectedItemProperty());
		
		filesListView.setItems(viewModel.subItemsProperty());
	}
	
	@FXML
	public void triggerMenu(MouseEvent event){
		
		if(isMenuOpen){
			hideMenu.play();
		}
		else{
			menuPane.toFront();
			showMenu.play();
		}
	}
	
	final Animation showMenu = new Transition() {
        { setCycleDuration(Duration.millis(250)); 
          onFinishedProperty().set(new EventHandler<ActionEvent>() {
              @Override 
              public void handle(ActionEvent actionEvent) {
            	  isMenuOpen = true;
            	  menuTriggerImage.setImage(menuUp);
              }
           });
        }
        protected void interpolate(double frac) {
          final double curWidth = (menuPane.getPrefHeight() - triggerPane.getPrefHeight()) * frac;
          menuPane.setLayoutY(-(menuPane.getPrefHeight() - triggerPane.getPrefHeight()) + curWidth);
        }
     };
     
     final Animation hideMenu = new Transition() {
         { setCycleDuration(Duration.millis(250)); 
         	onFinishedProperty().set(new EventHandler<ActionEvent>() {
         		@Override 
         		public void handle(ActionEvent actionEvent) {
           	  		isMenuOpen = false;
           	  		menuPane.toBack();
           	  		menuTriggerImage.setImage(menuDown);
            	}
         	});
         }
         protected void interpolate(double frac) {
           final double curWidth = (menuPane.getPrefHeight() - triggerPane.getPrefHeight()) * frac;
           menuPane.setLayoutY(0 - curWidth);
         }
      };
}
