package gui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class NavigationItem {

	private StringProperty name = new SimpleStringProperty();
	
	private ObservableList<NavigationItem> subItems = FXCollections.observableArrayList();
	
	private BooleanProperty isFolder = new SimpleBooleanProperty(false);
	
	private NavigationItem parent;
	
	public NavigationItem(){
		subItems.addListener((ListChangeListener<NavigationItem>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    change.getAddedSubList().forEach(item -> {
                        item.setParent(NavigationItem.this);
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
	
	public NavigationItem(String name){
        this();
        this.setName(name);
    }
	
	protected void setParent(NavigationItem parentItem){
        this.parent = parentItem;
    }
	
	public void setName(String name) {
        this.name.set(name);
    }
	
	public String getName() {
        return this.name.get();
    }

	public boolean isFolder() {
		return isFolder.get();
	}

	public void setIsFolder(boolean isFolder) {
		this.isFolder.set(isFolder);
	}

	public ObservableList<NavigationItem> getSubItems(){
        return subItems;
    }
	
	@Override
    public String toString() {
        return getName();
    }
}
