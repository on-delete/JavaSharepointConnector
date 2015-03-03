package gui.sharepointmain;

import gui.model.NavigationItem;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class NavigationItemCell extends TreeCell<NavigationItem>{
	
	private NavigationItem navigationItem;

	@Override
    public void updateItem(NavigationItem navigationItem, boolean empty) {
		this.navigationItem = navigationItem;
        super.updateItem(navigationItem, empty);
        if (empty) {
            setGraphic(null);
        } else {
        	setGraphic(createNavigationItemContent());
        }
    }
	
	private HBox createNavigationItemContent(){
		HBox navigationItemContent = new HBox();
		navigationItemContent.setSpacing(6);
		navigationItemContent.getChildren().add(createNavigationItemImage());
		navigationItemContent.getChildren().add(createNavigationItemLabel());

		return navigationItemContent;
	}

	private Label createNavigationItemLabel() {
		Label itemLabel = new Label(navigationItem.getName());
		itemLabel.setFont(Font.font("Segoe UI", 12));
		
		return itemLabel;
	}

	private ImageView createNavigationItemImage() {
		return new ImageView(new Image("images/icons/folder.png"));
	}
}
