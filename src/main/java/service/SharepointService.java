package service;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import util.UserCredentials;
import common.ErrorMessages;
import common.SharepointRessource;
import connector.Connector;

@Singleton
public class SharepointService {

	private Connector instance;
	private Sardine sardine;
	
	public SharepointService(){

	}
	
	public void initialize(String url, UserCredentials userCredentials){
		sardine = SardineFactory.begin(userCredentials.getUsername(), userCredentials.getPassword());
		
		instance = new Connector(url, sardine);
	}
	
	public List<SharepointRessource> getSharepointFiles(){
		List<SharepointRessource> list = instance.getItems();
		
		for(SharepointRessource ressource : list){
			crawl(ressource);
		}
		
		return list;
	}
	
	private void crawl(SharepointRessource ressource){
		if(ressource.isFolder()){
			if(ressource.getSubItems()!=null && ressource.getSubItems().size()>0 )
				for(SharepointRessource res : ressource.getSubItems()){
					crawl(res);
				}
		}
		else{
			//instance.getItem(ressource);
		}
	}
	
	public int testSharepointConnection() {
		try{
			instance.testConnection();
			return ErrorMessages.SUCCESS;
		}
		catch(IOException e){
			if(e.toString().contains("404 NOT FOUND")){
				System.out.println("Sharepoint Location not found");
				return ErrorMessages.NOT_FOUND;
			}
			else if(e.toString().contains("401")){
				System.out.println("Username or Password wrong");
				return ErrorMessages.NOT_AUTHORIZED;
			}
			else{
				System.out.println("Internal Error");
				return ErrorMessages.INTERNAL_ERROR;
			}
		}
	}
}
