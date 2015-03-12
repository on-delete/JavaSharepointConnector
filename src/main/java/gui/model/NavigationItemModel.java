package gui.model;

import javax.inject.Singleton;

@Singleton
public class NavigationItemModel {

	private NavigationItem rootItem;
	
    public void setRoot(String name){
        rootItem = new NavigationItem(name);
    }
    
    public NavigationItem getRootItem(){
    	return this.rootItem;
    }
}
