package service.connector;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import service.model.SharepointModel;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;

public class Connector {
	
	private String url;
	private String host;
	private Sardine sardine;
	
	public Connector(String url, Sardine sardine){
		this.url = url;
		this.sardine = sardine;
		
		URI uri = URI.create(this.url);
		host = uri.getScheme() + "://"+ uri.getHost();
	}
	
	public void testConnection() throws IOException{
		sardine.list(url);
	}
	
	public List<SharepointModel> getItems(){
		List<SharepointModel> itemList = new ArrayList<SharepointModel>();
		addSubItems(itemList, null);
		return itemList;
	}
	
	private void addSubItems(List<SharepointModel> subList, SharepointModel parentElement){
		boolean isFolder = false;
		
		List<DavResource> resources = new ArrayList<DavResource>();
		try{
			if(parentElement==null)
				resources = sardine.list(url);
			else{
				resources = sardine.list(host+parentElement.getHrefAsURI());
			}
		} catch(IOException e){
			e.printStackTrace();
		}
		
		for(int i = 1; i < resources.size(); i++){
			isFolder = false;
			DavResource resource = resources.get(i);
			String[] splitted = resource.getDisplayName().split("\\.");
			
			/*Ordner und Dateien mit Endung .aspx werden ignoriert, da es keine Zugriffsberechtigung über diese Funktion gibt.*/
			if(!splitted[splitted.length-1].equals("aspx") && !splitted[0].equals("Forms")){
				if (resource.getCustomProps().get("isFolder") != null) {
					isFolder = true;
					SharepointModel sharepointRessourceNew = mapRessources(resource, isFolder);
					subList.add(sharepointRessourceNew);
					addSubItems(sharepointRessourceNew.getSubItems(), sharepointRessourceNew);
				}
				else{
					subList.add(mapRessources(resource, isFolder));
				}
				
				Collections.sort(subList, comparatorByItemNameObject);
			}
		}
	}
	
	private SharepointModel mapRessources(DavResource ressource, boolean isFolder){
		SharepointModel sharepointRessource;
		
		Date creation = null;
		Date lastModified = null;
		String href = null;
		String modifiedBy = null;
		String displayName = null;
		
		creation = ressource.getCreation();
		lastModified = ressource.getModified();
		href = ressource.getPath();
		displayName = ressource.getDisplayName();
		
		if(isFolder){
			sharepointRessource = new SharepointModel(creation, lastModified, displayName, href, isFolder);
			if(sharepointRessource.getSubItems()==null){
				sharepointRessource.setSubItems(new ArrayList<SharepointModel>());
			}
		}
		else{
			if(ressource.getCustomProps().get("modifiedby") != null){
				modifiedBy = ressource.getCustomProps().get("modifiedby");
				sharepointRessource = new SharepointModel(creation, lastModified, displayName, href, isFolder, modifiedBy);
			}
			else
				sharepointRessource = new SharepointModel(creation, lastModified, displayName, href, isFolder, null);
		}
		
		return sharepointRessource;
	}
	
	//TODO Has to be reworked!
	/*public void getItem(SharepointModel ressource){
		try {
			InputStream is = sardine.get(Constants.sharepointURL+ressource.getHrefAsURI());
			
			ressource.setFileContent(is);
			
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	private Comparator<? super SharepointModel> comparatorByItemNameObject = new Comparator<SharepointModel>() {
        @Override
        public int compare(SharepointModel o1, SharepointModel o2) {
            return o1.getDisplayName().compareToIgnoreCase(o2.getDisplayName());
        }
    };
}
