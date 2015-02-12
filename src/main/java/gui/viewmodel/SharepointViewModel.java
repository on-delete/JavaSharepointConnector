package gui.viewmodel;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeItem;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import common.SharepointRessource;
import service.SharepointService;
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
		List<SharepointRessource> sharepointList = service.getSharepointFiles();

		initTreeViewItems(sharepointList);
	}
	
	private void initTreeViewItems(List<SharepointRessource> sharepointList){
		TreeViewListItem rootItem = treeViewListModel.getRoot("ISODokumentenablage");
		
		rootNode.setValue(rootItem);
		
		for(SharepointRessource ressource : sharepointList){
			TreeViewListItem subItem = new TreeViewListItem();
			
			if(ressource.isFolder() && ressource.getSubItems()!=null){
				addSubItems(ressource.getSubItems(), subItem);
			}
			
			subItem.setName(ressource.getDisplayName());
			
			rootItem.getSubItems().add(subItem);
		}
	}
	
	private void addSubItems(List<SharepointRessource> subList, TreeViewListItem item){
		for(SharepointRessource ressource : subList){
			TreeViewListItem subItem = new TreeViewListItem();
			
			if(ressource.isFolder() && ressource.getSubItems()!=null){
				addSubItems(ressource.getSubItems(), subItem);
			}
			
			subItem.setName(ressource.getDisplayName());
			
			item.getSubItems().add(subItem);
		}
	}
}
