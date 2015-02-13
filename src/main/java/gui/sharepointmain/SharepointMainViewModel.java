package gui.sharepointmain;

import gui.model.TreeViewListItem;
import gui.model.TreeViewListModel;
import gui.util.RecursiveTreeItem;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;

import javax.inject.Inject;

import service.SharepointService;
import service.model.SharepointModel;
import de.saxsys.mvvmfx.ViewModel;

public class SharepointMainViewModel implements ViewModel{
	
	private ObjectProperty<String> listRootName = new SimpleObjectProperty<String>();
	private ObjectProperty selectedTreeItem = new SimpleObjectProperty();
	
	private TreeItem<TreeViewListItem> rootNode;

	@Inject
	TreeViewListModel treeViewListModel;
	
	public ObjectProperty<String> listRootNameProperty() {
		return listRootName;
	}

	public ObjectProperty selectedTreeItemProperty() {
		return selectedTreeItem;
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

	public void getSharepointData() {
		initTreeViewItems();
	}
	
	private void initTreeViewItems(){
		List<SharepointModel> sharepointList = service.getSharepointFiles();
		String[] splittedUrl = service.getUrl().split("/");
		TreeViewListItem rootItem = treeViewListModel.getRoot(splittedUrl[splittedUrl.length-1]);
		
		rootNode.setValue(rootItem);
		
		addChildItems(sharepointList, rootItem);
	}
	
	private void addChildItems(List<SharepointModel> subList, TreeViewListItem parentItem){
		for(SharepointModel ressource : subList){
			TreeViewListItem childItem = new TreeViewListItem();
			
			if(ressource.isFolder() && ressource.getSubItems()!=null){
				addChildItems(ressource.getSubItems(), childItem);
			}
			
			childItem.setName(ressource.getDisplayName());
			
			parentItem.getSubItems().add(childItem);
		}
	}
}
