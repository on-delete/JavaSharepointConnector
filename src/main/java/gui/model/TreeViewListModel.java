package gui.model;

import javax.inject.Singleton;

@Singleton
public class TreeViewListModel {

	private TreeViewListItem rootItem;
	
    public void setRoot(String name){
        rootItem = new TreeViewListItem(name);
    }
    
    public TreeViewListItem getRootItem(){
    	return this.rootItem;
    }
}
