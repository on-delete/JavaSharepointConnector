package gui.sharepointmain;

import gui.model.ListViewItem;
import gui.model.TreeViewListItem;
import gui.model.TreeViewListModel;
import gui.util.RecursiveTreeItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	private ObservableList<ListViewItem> subItems = FXCollections.observableArrayList();
	
	private TreeItem<TreeViewListItem> rootNode;
	private List<SharepointModel> sharepointList;

	@Inject
	TreeViewListModel treeViewListModel;

	public ObjectProperty<TreeItem<TreeViewListItem>> selectedTreeItemProperty() {
		return selectedTreeItem;
	}
	
	public ObservableList<ListViewItem> subItemsProperty() {
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
		
		selectedTreeItem.addListener(new ChangeListener<TreeItem<TreeViewListItem>>(){
			
			@Override
			public void changed(
					ObservableValue<? extends TreeItem<TreeViewListItem>> observable,
					TreeItem<TreeViewListItem> oldValue,
					TreeItem<TreeViewListItem> newValue) {
				if(newValue==null){
					//In this case, the parent tree item is closed, so that in the first change the new tree item is null, this should be catched here.
					//In the next change the value is set to the closed parent tree item.
				}
				else if(newValue.getValue().equals(treeViewListModel.getRootItem())){
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
			subItems.add(new ListViewItem(subItem.getDisplayName(), subItem.isFolder()));
		}
		
		Collections.sort(subItems, comparatorByIsFolder);
	}
	
	private Comparator<? super ListViewItem> comparatorByIsFolder = new Comparator<ListViewItem>() {
        @Override
        public int compare(ListViewItem o1, ListViewItem o2) {
        	boolean v1 = o1.isFolder();
            boolean v2 = o2.isFolder();
            if( v1 && ! v2 ) {
                return -1;
            }
            if( ! v1 && v2 ) {
                return +1;
            }
            return 0;
        }
    };
}
