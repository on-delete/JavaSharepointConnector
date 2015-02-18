package service;

import java.util.ArrayList;
import java.util.List;

import service.model.SharepointModel;

public class SharepointServiceMockData {

	public SharepointServiceMockData(){
		
	}
	
	public List<SharepointModel> getSharepointItems(){
		List<SharepointModel> itemList = new ArrayList<SharepointModel>();
		
		SharepointModel sharepointRessource1 = new SharepointModel(null, null, "04 Übersicht Fremdleistungen und Unterauftragnehmer", null, true);
		sharepointRessource1.setSubItems(new ArrayList<SharepointModel>());
		SharepointModel sharepointRessource2 = new SharepointModel(null, null, "HW-Fleischhauer", null, true);
		sharepointRessource2.setSubItems(new ArrayList<SharepointModel>());
		SharepointModel sharepointRessource3 = new SharepointModel(null, null, "Lizenzen", null, true);
		sharepointRessource3.setSubItems(new ArrayList<SharepointModel>());
		SharepointModel sharepointRessource4 = new SharepointModel(null, null, "Presse-Möller-Horcher", null, true);
		sharepointRessource4.setSubItems(new ArrayList<SharepointModel>());
		SharepointModel sharepointRessource31 = new SharepointModel(null, null, "OpenSource", null, true);
		sharepointRessource31.setSubItems(new ArrayList<SharepointModel>());
		SharepointModel sharepointRessource32 = new SharepointModel(null, null, "WiBu-Softwareschutz", null, true);
		sharepointRessource32.setSubItems(new ArrayList<SharepointModel>());
		
		SharepointModel sharepointRessource21 = new SharepointModel(null, null, "20120904_TVC_Konzept Telepresence Office.pdf", null, false, null);
		SharepointModel sharepointRessource22 = new SharepointModel(null, null, "406226_ANG_Saxonia_Böhme_StandardTelepresence.pdf", null, false, null);
		
		SharepointModel sharepointRessource311 = new SharepointModel(null, null, "Aufgabenboard-Fremdprodukte-Lizenzen.xlx", null, false, null);
		SharepointModel sharepointRessource312 = new SharepointModel(null, null, "OSS_Management-Summary.doc", null, false, null);
		SharepointModel sharepointRessource313 = new SharepointModel(null, null, "ManagementCircle.pdf", null, false, null);
		
		sharepointRessource1.getSubItems().add(sharepointRessource2);
		sharepointRessource1.getSubItems().add(sharepointRessource3);
		sharepointRessource1.getSubItems().add(sharepointRessource4);
		
		sharepointRessource3.getSubItems().add(sharepointRessource31);
		sharepointRessource3.getSubItems().add(sharepointRessource32);
		
		sharepointRessource2.getSubItems().add(sharepointRessource21);
		sharepointRessource2.getSubItems().add(sharepointRessource22);
		
		sharepointRessource31.getSubItems().add(sharepointRessource311);
		sharepointRessource31.getSubItems().add(sharepointRessource312);
		sharepointRessource31.getSubItems().add(sharepointRessource313);
		
		itemList.add(sharepointRessource1);
		
		return itemList;
	}
}
