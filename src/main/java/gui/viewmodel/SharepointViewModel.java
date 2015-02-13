package gui.viewmodel;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TreeItem;

import javax.inject.Inject;

import service.SharepointService;
import service.model.SharepointModel;
import de.saxsys.mvvmfx.ViewModel;

public class SharepointViewModel implements ViewModel{
	
	private ObjectProperty<String> listRootName = new SimpleObjectProperty<String>();
	
	private TreeItem<TreeViewListItem> rootNode;
	
	@Inject
	TreeViewListModel treeViewListModel;
	
	public ObjectProperty<String> listRootNameProperty() {
		return listRootName;
	}

	private SharepointService service;
	
	@Inject
	public SharepointViewModel(SharepointService service){
		this.service = service;
		rootNode = new RecursiveTreeItem<>(TreeViewListItem::getSubItems);
	}

    public TreeItem<TreeViewListItem> getRootNode(){
        return rootNode;
    }

	public void getSharepointData() {
		List<SharepointModel> sharepointList = service.getSharepointFiles();

		initTreeViewItems(sharepointList);
	}
	
	private void initTreeViewItems(List<SharepointModel> sharepointList){
		String[] splittedUrl = service.getUrl().split("/");
		TreeViewListItem rootItem = treeViewListModel.getRoot(splittedUrl[splittedUrl.length-1]);
		
		rootNode.setValue(rootItem);
		
		for(SharepointModel ressource : sharepointList){
			TreeViewListItem subItem = new TreeViewListItem();
			
			if(ressource.isFolder() && ressource.getSubItems()!=null){
				addSubItems(ressource.getSubItems(), subItem);
			}
			
			subItem.setName(ressource.getDisplayName());
			
			rootItem.getSubItems().add(subItem);
		}
	}
	
	private void addSubItems(List<SharepointModel> subList, TreeViewListItem item){
		for(SharepointModel ressource : subList){
			TreeViewListItem subItem = new TreeViewListItem();
			
			if(ressource.isFolder() && ressource.getSubItems()!=null){
				addSubItems(ressource.getSubItems(), subItem);
			}
			
			subItem.setName(ressource.getDisplayName());
			
			item.getSubItems().add(subItem);
		}
	}
}
