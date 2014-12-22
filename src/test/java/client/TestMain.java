package client;

import java.io.IOException;
import java.util.List;

import com.github.sardine.DavAcl;
import com.github.sardine.DavResource;
import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import common.Project;
import common.SharepointRessource;
import connector.Connector;
import util.UserCredentials;

public class TestMain {
	
	static final String sharepointLink = "https://portal.saxsys.de/projekte/";
	
	private static Connector instance;
	
	public static void main(String[] args) {
		
		UserCredentials userCredentials = new UserCredentials(args[0] , args[1]);

			instance = new Connector("https://portal.saxsys.de/projects/aquarium/ISODokumentenablage", userCredentials);
			
			List<SharepointRessource> list = instance.getItems();
			
			for(SharepointRessource ressource : list){
				crawl(ressource);
			}
			
			System.out.println();
	}
	
	private static void crawl(SharepointRessource ressource){
		if(ressource.isFolder()){
			if(ressource.getSubItems()!=null && ressource.getSubItems().size()>0 )
				for(SharepointRessource res : ressource.getSubItems()){
					crawl(res);
				}
		}
		else{
			instance.getItem(ressource);
		}
	}
	
	
}
