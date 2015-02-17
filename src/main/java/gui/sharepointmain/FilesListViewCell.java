package gui.sharepointmain;

import gui.model.ListViewItem;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class FilesListViewCell extends ListCell<ListViewItem> {

	private HBox listCellContent;
	private ListViewItem listViewItem;
	private String[] iconSet = {"bmp", "c", "cpp", "css", "dat", "doc", "docx", "exe", "gif", "html", "java", "jpg", "js", "pdf", "php", "png", "ppt", "pptx", "py", "rar",
								"rtf", "sql", "txt", "xls", "xlsx", "xml", "zip"};

	
	@Override
	public void updateItem(ListViewItem listViewItem, boolean empty){
		this.listViewItem = listViewItem;
		super.updateItem(listViewItem,empty);
	    if(listViewItem!=null){
	    	setGraphic(createListCellContent());
	    }
	}	
	
	private HBox createListCellContent(){
		if(listCellContent==null){
			listCellContent = new HBox();
		}
		
		listCellContent.getChildren().add(createItemImage());
		listCellContent.getChildren().add(createItemLabel());
		return listCellContent;
	}

	private ImageView createItemLabel() {
		return new ImageView(new Image("images/triangle_down.png"));
	}

	private Node createItemImage() {
		// TODO Auto-generated method stub
		return null;
	}
}
