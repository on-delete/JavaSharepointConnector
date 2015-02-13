package gui.sharepointmain;

import gui.model.TreeViewListItem;
import gui.model.TreeViewListModel;
import gui.util.RecursiveTreeItem;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import javax.inject.Inject;

import service.SharepointService;
import service.model.SharepointModel;
import de.saxsys.mvvmfx.ViewModel;

public class SharepointMainViewModel implements ViewModel{

	private ObjectProperty<TreeItem<TreeViewListItem>> selectedTreeItem = new SimpleObjectProperty<TreeItem<TreeViewListItem>>();
	private ObservableList<String> subItems = FXCollections.observableArrayList();
	
	private TreeItem<TreeViewListItem> rootNode;
	private List<SharepointModel> sharepointList;

	@Inject
	TreeViewListModel treeViewListModel;

	public ObjectProperty<TreeItem<TreeViewListItem>> selectedTreeItemProperty() {
		return selectedTreeItem;
	}
	
	public ObservableList<String> subItemsProperty() {
		return subItems;
	}

	private SharepointService service;

	@Inject
	public SharepointMainViewModel(SharepointService service){
		this.service = service;
		rootNode = new RecursiveTreeItem<>(TreeViewListItem::getSubItems);
	}

    public TreeItem<TreeViewListItem> getRootNode(){
        return rootNode;
    }
	
	public void initTreeViewItems(){
		this.sharepointList = service.getSharepointFiles();
		String[] splittedUrl = service.getUrl().split("/");
		treeViewListModel.setRoot(splittedUrl[splittedUrl.length-1]);
		TreeViewListItem rootItem = treeViewListModel.getRootItem();
		
		rootNode.setValue(rootItem);
		
		addChildItemsTreeView(sharepointList, rootItem);

		setSubListItems(sharepointList);
		
		selectedTreeItem.addListener(new ChangeListener<TreeItem<TreeViewListItem>>(){@Override
			public void changed(
					ObservableValue<? extends TreeItem<TreeViewListItem>> observable,
					TreeItem<TreeViewListItem> oldValue,
					TreeItem<TreeViewListItem> newValue) {
				if(newValue.getValue().equals(treeViewListModel.getRootItem())){
					setSubListItems(sharepointList);
				}
				else{
					addSubItemsListView(newValue.getValue().getName(), sharepointList);
				}
			}
	      });
	}
	
	private void addChildItemsTreeView(List<SharepointModel> subList, TreeViewListItem parentItem){
		for(SharepointModel ressource : subList){
			TreeViewListItem childItem = new TreeViewListItem();
			
			if(ressource.isFolder() && ressource.getSubItems()!=null){
				addChildItemsTreeView(ressource.getSubItems(), childItem);
				childItem.setName(ressource.getDisplayName());
				parentItem.getSubItems().add(childItem);
			}
		}
	}
	
	private void addSubItemsListView(String itemName, List<SharepointModel> subSharepointList){
		if(subSharepointList!=null){
			for(SharepointModel subItem : subSharepointList){
				if(subItem.getDisplayName().equals(itemName)){
					setSubListItems(subItem.getSubItems());
				}
				else{
					addSubItemsListView(itemName, subItem.getSubItems());
				}
			}
		}
	}
	
	private void setSubListItems(List<SharepointModel> subSharepointList){
		subItems.clear();
		
		for(SharepointModel subItem : subSharepointList){
			subItems.add(subItem.getDisplayName());
		}
	}
}
