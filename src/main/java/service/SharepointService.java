package service;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import service.connector.Connector;
import service.model.SharepointModel;
import service.util.URIEncoder;
import service.util.UserCredentials;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;
import common.ErrorMessages;

@Singleton
public class SharepointService {

	private Connector instance;
	private Sardine sardine;
	
	private String url;
	private UserCredentials userCredentials;
	
	public SharepointService(){

	}
	
	public void initialize(String url, UserCredentials userCredentials){
		this.url = url;
		this.userCredentials = userCredentials;
		
		sardine = SardineFactory.begin(userCredentials.getUsername(), userCredentials.getPassword());
		
		instance = new Connector(URIEncoder.encodeURI(url), sardine);
	}
	
	public List<SharepointModel> getSharepointFiles(){
		List<SharepointModel> list = instance.getItems();
		
		for(SharepointModel ressource : list){
			crawl(ressource);
		}
		
		return list;
	}
	
	private void crawl(SharepointModel ressource){
		if(ressource.isFolder()){
			if(ressource.getSubItems()!=null && ressource.getSubItems().size()>0 )
				for(SharepointModel res : ressource.getSubItems()){
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

	public String getUrl() {
		return url;
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
}
