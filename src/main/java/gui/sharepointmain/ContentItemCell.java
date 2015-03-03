package gui.sharepointmain;

import gui.model.ContentItem;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import common.Constants;

public class ContentItemCell extends ListCell<ContentItem> {

	private ContentItem contentItem;
	private SharepointMainViewModel viewModel;
	
	private Label itemLabel;

	public ContentItemCell(SharepointMainViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void updateItem(ContentItem contentItem, boolean empty){
		this.contentItem = contentItem;
		super.updateItem(contentItem,empty);
	    if(empty){
	    	setGraphic(null);
	    	setOnMouseClicked(null);
	    }
	    else{
	    	setGraphic(createContentItemCellContent());
	    	if(contentItem.isFolder()){
		    	setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						if(event.getClickCount()>1){
							ContentItemCell eventSource = (ContentItemCell)event.getSource();
							viewModel.handleContentDoubleClick(eventSource.getItemLabel().getText());
						}
					}
				});
	    	}
	    }
	}	
	
	private HBox createContentItemCellContent(){
		HBox contentItemContent = new HBox();
		contentItemContent.setSpacing(10);
		contentItemContent.getChildren().add(createContentItemImage());
		contentItemContent.getChildren().add(createContentItemLabel());

		return contentItemContent;
	}

	private Label createContentItemLabel() {
		itemLabel = new Label(contentItem.getName());
		itemLabel.setFont(Font.font("Segoe UI", 12));
		
		return itemLabel;
	}

	private ImageView createContentItemImage() {
		if(contentItem.isFolder()){
			return new ImageView(new Image("images/icons/folder.png"));
		}
		else{
			String[] seperated = contentItem.getName().split("\\.");
			String fileExtension = seperated[seperated.length-1];
			if(Constants.ICON_SET.contains(fileExtension)){
				return new ImageView(new Image("images/icons/"+fileExtension+".png"));
			}
			else{
				return new ImageView(new Image("images/icons/blank.png"));
			}
		}
	}
	
	public Label getItemLabel(){
		return this.itemLabel;
	}
}
