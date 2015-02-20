package gui.sharepointmain;

import gui.model.ListViewItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import common.Constants;

public class FilesListViewCell extends ListCell<ListViewItem> {

	private ListViewItem listViewItem;

	@Override
	public void updateItem(ListViewItem listViewItem, boolean empty){
		this.listViewItem = listViewItem;
		super.updateItem(listViewItem,empty);
	    if(empty){
	    	setGraphic(null);
	    }
	    else{
	    	setGraphic(createListCellContent());
	    }
	}	
	
	private HBox createListCellContent(){
		HBox listCellContent = new HBox();
		listCellContent.setSpacing(10);
		listCellContent.getChildren().add(createItemImage());
		listCellContent.getChildren().add(createItemLabel());

		return listCellContent;
	}

	private Label createItemLabel() {
		Label itemLabel = new Label(listViewItem.getName());
		itemLabel.setFont(Font.font("Segoe UI", 12));
		
		return itemLabel;
	}

	private ImageView createItemImage() {
		if(listViewItem.isFolder()){
			return new ImageView(new Image("images/icons/folder.png"));
		}
		else{
			String[] seperated = listViewItem.getName().split("\\.");
			String fileExtension = seperated[seperated.length-1];
			if(Constants.ICON_SET.contains(fileExtension)){
				return new ImageView(new Image("images/icons/"+fileExtension+".png"));
			}
			else{
				return new ImageView(new Image("images/icons/blank.png"));
			}
		}
	}
}
