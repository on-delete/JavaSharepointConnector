package connector;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.sardine.DavResource;
import com.github.sardine.Sardine;

import common.Constants;
import common.SharepointRessource;

public class Connector {
	
	private String url;
	private Sardine sardine;
	
	public Connector(String url, Sardine sardine){
		this.url = url;
		this.sardine = sardine;
	}
	
	public void testConnection() throws IOException{
		sardine.list(url);
	}
	
	public List<SharepointRessource> getItems(){
		List<SharepointRessource> itemList = new ArrayList<SharepointRessource>();
		boolean isFolder = false;
		
		List<DavResource> resources = new ArrayList<DavResource>();
		try{
			resources = sardine.list(url);
		} catch(IOException e){
			e.printStackTrace();
		}
		
		for(int i = 1; i < resources.size(); i++){
			DavResource ressource = resources.get(i);
			
			String[] splitted = ressource.getDisplayName().split("\\.");
			
			/*Ordner und Dateien mit Endung .aspx werden ignoriert, da es keine Zugriffsberechtigung über diese Funktion gibt.*/
			if(!splitted[splitted.length-1].equals("aspx")){
				if (ressource.getCustomProps().get("isFolder") != null) {
					isFolder = true;
					SharepointRessource sharepointRessource = mapRessources(ressource, isFolder);
					itemList.add(sharepointRessource);
					getItems(sharepointRessource);
				}
				else{
					itemList.add(mapRessources(ressource, isFolder));
				}
			}
		}
		
		return itemList;
	}
	
	private void getItems(SharepointRessource sharepointRessource){
		boolean isFolder = false;
		
		List<DavResource> resources = new ArrayList<DavResource>();
		try{
			resources = sardine.list(Constants.sharepointURL + sharepointRessource.getHrefAsURI());
		}catch(IOException e){
			e.printStackTrace();
		}
		
		for(int i = 1; i < resources.size(); i++){
			DavResource ressource = resources.get(i);
			
			String[] splitted = ressource.getDisplayName().split("\\.");
			
			/*Ordner und Dateien mit Endung .aspx werden ignoriert, da es keine Zugriffsberechtigung über diese Funktion gibt.*/
			if(!splitted[splitted.length-1].equals("aspx")){
				if (ressource.getCustomProps().get("isFolder") != null) {
					isFolder = true;
					SharepointRessource sharepointRessourceNew = mapRessources(ressource, isFolder);
					if(sharepointRessource.getSubItems()==null){
						sharepointRessource.setSubItems(new ArrayList<SharepointRessource>());
					}
					
					sharepointRessource.getSubItems().add(sharepointRessourceNew);
					getItems(sharepointRessourceNew);
				}
				else{
					if(sharepointRessource.getSubItems()==null){
						sharepointRessource.setSubItems(new ArrayList<SharepointRessource>());
					}
						
					sharepointRessource.getSubItems().add(mapRessources(ressource, isFolder));
				}
			}
		}
	}
	
	private SharepointRessource mapRessources(DavResource ressource, boolean isFolder){
		SharepointRessource sharepointRessource;
		
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
			sharepointRessource = new SharepointRessource(creation, lastModified, displayName, href, isFolder);
		}
		else{
			if(ressource.getCustomProps().get("modifiedby") != null){
				modifiedBy = ressource.getCustomProps().get("modifiedby");
				sharepointRessource = new SharepointRessource(creation, lastModified, displayName, href, isFolder, modifiedBy);
			}
			else
				sharepointRessource = new SharepointRessource(creation, lastModified, displayName, href, isFolder, null);
		}
		
		return sharepointRessource;
	}
	
	public void getItem(SharepointRessource ressource){
		try {
			InputStream is = sardine.get(Constants.sharepointURL+ressource.getHrefAsURI());
			
			ressource.setFileContent(is);
			
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
