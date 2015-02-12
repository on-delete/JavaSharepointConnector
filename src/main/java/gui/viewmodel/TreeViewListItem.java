package gui.viewmodel;

import java.util.Collections;
import java.util.Comparator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class TreeViewListItem {

	private StringProperty name = new SimpleStringProperty();
	
	private ObservableList<TreeViewListItem> subItems = FXCollections.observableArrayList();
	
	private TreeViewListItem parent;
	
	public TreeViewListItem(){
		subItems.addListener((ListChangeListener<TreeViewListItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(item -> {
                        item.setParent(TreeViewListItem.this);
                    });
                    
                }

                if (change.wasRemoved()) {
                    change.getRemoved().forEach(item -> {
                        item.setParent(null);
                    });
                }
            }
        });
	}
	
	public TreeViewListItem(String name){
        this();
        this.setName(name);
    }
	
	protected void setParent(TreeViewListItem parentItem){
        this.parent = parentItem;
    }
	
	public void setName(String name) {
        this.name.set(name);
    }
	
	public String getName() {
        return this.name.get();
    }
	
	public ObservableList<TreeViewListItem> getSubItems(){
        return subItems;
    }
	
	@Override
    public String toString() {
        return getName();
    }
}
