package gui.model;

import javax.inject.Singleton;

@Singleton
public class TreeViewListModel {

    public TreeViewListItem getRoot(String name){
        return new TreeViewListItem(name);
    }
}
