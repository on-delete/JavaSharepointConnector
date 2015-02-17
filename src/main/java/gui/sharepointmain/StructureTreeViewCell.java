package gui.sharepointmain;

import gui.model.TreeViewListItem;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class StructureTreeViewCell extends TreeCell<TreeViewListItem>{
	private TreeViewListItem item;
	
	@Override
    public void updateItem(TreeViewListItem item, boolean empty) {
		this.item = item;
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
        	setGraphic(createTreeCellContent());
        }
    }
	
	private HBox createTreeCellContent(){
		HBox treeCellContent = new HBox();
		treeCellContent.setSpacing(10);
		treeCellContent.getChildren().add(createItemImage());
		treeCellContent.getChildren().add(createItemLabel());

		return treeCellContent;
	}

	private Label createItemLabel() {
		Label itemLabel = new Label(item.getName());
		itemLabel.setFont(Font.font("Segoe UI Symbol", 12));
		
		return itemLabel;
	}

	private ImageView createItemImage() {
		return new ImageView(new Image("images/icons/folder.png"));
	}
}
