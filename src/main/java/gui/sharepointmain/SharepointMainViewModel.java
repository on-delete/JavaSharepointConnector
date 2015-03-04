package gui.sharepointmain;

import static org.reactfx.EventStreams.changesOf;
import gui.model.ContentItem;
import gui.model.NavigationItem;
import gui.model.NaviagtionItemModel;
import gui.util.RecursiveTreeItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.inject.Inject;

import org.fxmisc.undo.UndoManager;
import org.fxmisc.undo.UndoManagerFactory;
import org.reactfx.Change;
import org.reactfx.EventStream;

import service.SharepointService;
import service.model.SharepointModel;
import de.saxsys.mvvmfx.ViewModel;

public class SharepointMainViewModel implements ViewModel{

	private ObjectProperty<TreeItem<NavigationItem>> selectedNaviagtionItem = new SimpleObjectProperty<TreeItem<NavigationItem>>();
	private ObjectProperty<TreeItem<NavigationItem>> navigationItemHistory = new SimpleObjectProperty<TreeItem<NavigationItem>>();
	private ObservableList<ContentItem> contentItemList = FXCollections.observableArrayList();
	private IntegerProperty viewNumber = new SimpleIntegerProperty(2);
	
	private TreeItem<NavigationItem> rootNode;
	private List<SharepointModel> sharepointItemList;
	
	private UndoManager undoManager;

	@Inject
	private NaviagtionItemModel navigationItemModel;

	public ObjectProperty<TreeItem<NavigationItem>> selectedNaviagtionItemProperty() {
		return selectedNaviagtionItem;
	}
	
	public ObservableList<ContentItem> contentItemListProperty() {
		return contentItemList;
	}
	
	public IntegerProperty viewNumberProperty() {
		return viewNumber;
	}
	
	public ObjectProperty<TreeItem<NavigationItem>> navigationItemHistoryProperty(){
		return navigationItemHistory;
	}
	
	public ObservableBooleanValue undoAvailableProperty(){
		return undoManager.undoAvailableProperty();
	}
	
	public ObservableBooleanValue redoAvailableProperty(){
		return undoManager.redoAvailableProperty();
	}
	
	private SharepointService service;
	
	@Inject
	public SharepointMainViewModel(SharepointService service){
		EventStream<Change<TreeItem<NavigationItem>>> selectedNavigationItemChanges =
		        changesOf(selectedNaviagtionItem);
		
		undoManager = UndoManagerFactory.unlimitedHistoryUndoManager(
				selectedNavigationItemChanges, // stream of changes to observe
				c -> navigationItemHistory.set(c.getNewValue()),  // function to redo a change
				c -> navigationItemHistory.set(c.getOldValue())); // function to undo a change
		
		this.service = service;
		this.sharepointItemList = this.service.getSharepointFiles();
		
		selectedNaviagtionItem.addListener((ChangeListener<TreeItem<NavigationItem>>) (observable, oldValue, newValue) -> {
			if (oldValue == null){
				undoManager.forgetHistory();
			}
			if (newValue == null) {
						// In this case, the parent tree item is closed, so that
						// in the first change the new tree item is null, this
						// should be catched here.
						// In the next change the value is set to the closed
						// parent tree item.
			} else if (newValue.getValue().equals(navigationItemModel.getRootItem())) {
				addItemsContent(null, sharepointItemList);
			} else {
				addItemsContent(newValue.getValue().getName(),sharepointItemList);
			}
		});
	}

    public TreeItem<NavigationItem> initRootNode(){
    	rootNode = new RecursiveTreeItem<>(NavigationItem::getSubItems);
    	String[] splittedUrl = service.getUrl().split("/");
		navigationItemModel.setRoot(splittedUrl[splittedUrl.length-1]);
		NavigationItem rootItem = navigationItemModel.getRootItem();
		
		rootNode.setValue(rootItem);
		addChildItemsNavigation(sharepointItemList, rootItem);
		addItemsContent(null, sharepointItemList);
    	
        return rootNode;
    }
    
    public void handleContentDoubleClick(String text) {
		addItemsContent(text, sharepointItemList);
	}
    
    public void undo(){
    	undoManager.undo();
    }
    
    public void redo(){
    	undoManager.redo();
    }
	
	private void addChildItemsNavigation(List<SharepointModel> subList, NavigationItem parentItem){
		for(SharepointModel ressource : subList){
			NavigationItem childItem = new NavigationItem();
			
			if(ressource.isFolder() && ressource.getSubItems()!=null){
				addChildItemsNavigation(ressource.getSubItems(), childItem);
				childItem.setIsFolder(ressource.isFolder());
				childItem.setName(ressource.getDisplayName());
				parentItem.getSubItems().add(childItem);
			}
		}
	}
	
	private void addItemsContent(String itemName, List<SharepointModel> subSharepointList){
		if(subSharepointList!=null){
			/*Rootnode-case*/
			if(itemName==null){
				contentItemList.clear();
				
				for(SharepointModel subItemTemp : subSharepointList){
					contentItemList.add(new ContentItem(subItemTemp.getDisplayName(), subItemTemp.isFolder()));
				}
				
				Collections.sort(contentItemList, comparatorByIsFolder);
			}
			else{
				for(SharepointModel subItem : subSharepointList){
					if(subItem.getDisplayName().equals(itemName)){
						contentItemList.clear();
						
						for(SharepointModel subItemTemp : subItem.getSubItems()){
							contentItemList.add(new ContentItem(subItemTemp.getDisplayName(), subItemTemp.isFolder()));
						}
						
						Collections.sort(contentItemList, comparatorByIsFolder);
					}
					else{
						addItemsContent(itemName, subItem.getSubItems());
					}
				}
			}
		}
	}
	
	private Comparator<? super ContentItem> comparatorByIsFolder = (o1, o2) -> {
		boolean v1 = o1.isFolder();
	    boolean v2 = o2.isFolder();
	    if( v1 && ! v2 ) {
	        return -1;
	    }
	    if( ! v1 && v2 ) {
	        return +1;
	    }
	    return 0;
	};
}
