package gui.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ListViewItem {

	private StringProperty name = new SimpleStringProperty();
	private BooleanProperty isFolder = new SimpleBooleanProperty();
	
	public ListViewItem(String name, boolean isFolder) {
		super();
		this.setName(name);
		this.setIsFolder(isFolder);
	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public boolean isFolder() {
		return isFolder.get();
	}
	public void setIsFolder(boolean isFolder) {
		this.isFolder.set(isFolder);
	}
	
	@Override
    public String toString() {
        return getName();
    }
}
