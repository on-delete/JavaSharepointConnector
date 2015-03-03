package service;

import java.io.IOException;
import java.util.List;

import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import service.connector.Connector;
import service.model.SharepointModel;
import service.util.URIEncoder;
import service.util.UserCredentials;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import common.Constants;

@Singleton
public class SharepointService {

	private Connector instance;
	private Sardine sardine;
	
	private String url;
	private UserCredentials userCredentials;
	
	private final static Logger LOGGER = /*Logger.getLogger(SharepointService.class);*/ LoggerFactory.getLogger(SharepointService.class);
	
	public SharepointService(){

	}
	
	public void initialize(String url, UserCredentials userCredentials){
		this.url = url;
		this.userCredentials = userCredentials;
		
		sardine = SardineFactory.begin(userCredentials.getUsername(), userCredentials.getPassword());
		
		instance = new Connector(URIEncoder.encodeURI(url), sardine);
	}
	
	public List<SharepointModel> getSharepointFiles(){
		if(Constants.WITHOUT_LOGIN){
			url = "http://test.de:8080/sharepoint/ISODokumentenablage";
			return new SharepointServiceMockData().getSharepointItems();
		}
		else{
			return instance.getItems();
		}
	}
	
	//TODO function to download the files from sharepoint needs to bew reworked!
//	public List<SharepointModel> getSharepointFiles(){
//		List<SharepointModel> list = instance.getItems();
//		
//		for(SharepointModel ressource : list){
//			crawl(ressource);
//		}
//		
//		return list;
//	}
//	
//	private void crawl(SharepointModel ressource){
//		if(ressource.isFolder()){
//			if(ressource.getSubItems()!=null && ressource.getSubItems().size()>0 )
//				for(SharepointModel res : ressource.getSubItems()){
//					crawl(res);
//				}
//		}
//		else{
//			//instance.getItem(ressource);
//		}
//	}
	
	public int testSharepointConnection() {
		try{
			instance.testConnection();
			return Constants.SUCCESS;
		}
		catch(IOException e){
			if(e.toString().contains("404 NOT FOUND")){
				LOGGER.error("Sharepoint Location not found");
				return Constants.NOT_FOUND;
			}
			else if(e.toString().contains("401")){
				LOGGER.error("Username or Password wrong");
				return Constants.NOT_AUTHORIZED;
			}
			else{
				LOGGER.error("Internal Error");
				return Constants.INTERNAL_ERROR;
			}
		}
	}

	public String getUrl() {
		return url;
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	
	public Connector getInstance(){
		return instance;
	}
}
