//package service;
//
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.io.IOException;
//import java.util.List;
//
//import org.junit.Test;
//
//import util.UserCredentials;
//
//import com.github.sardine.impl.SardineException;
//
//import common.Project;
//import common.SharepointRessource;
//
//public class SharepointRestServiceTest {
//
//	@Test
//	public void getAllProjectsShouldReturnList(){
//		
//		List<Project> projectList;
//		try {
//			projectList = ServiceConnector.getAllProjects(new UserCredentials("andre.krause", "Abitur200922"), "https://portal.saxsys.de/projekte/");
//			
//			assertTrue(projectList.size()>0);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void getAllProjects401Exception() {
//		List<Project> projectList;
//		
//		try {
//			projectList = ServiceConnector.getAllProjects(new UserCredentials("username", "test"), "https://portal.saxsys.de/projekte/");
//			fail("Exception not thrown");
//		} catch (IOException e) {
//			SardineException e1 = (SardineException) e;
//			assertTrue(e1.getStatusCode() == 401);
//		}
//	}
//	
//	@Test
//	public void getAllProjects400Exception(){
//		List<Project> projectList;
//		
//		try {
//			projectList = ServiceConnector.getAllProjects(new UserCredentials("andre.krause", "Abitur200922"), "https://portal.saxsys.de/projekt/");
//			fail("Exception not thrown");
//		} catch (IOException e) {
//			SardineException e1 = (SardineException) e;
//			assertTrue(e1.getStatusCode() == 400);
//		}
//	}
//	
//	@Test
//	public void getFolderShouldReturnFolderList(){
//		List<SharepointRessource> folderList;
//		
//		try{
//			folderList = ServiceConnector.getFolder(new UserCredentials("andre.krause", "Abitur200922"), "/projects/aquarium/ISODokumentenablage");
//			
//			assertTrue(folderList.size()>0);
//		}
//		catch(IOException e){
//			e.printStackTrace();
//			fail("Exception thrown");
//		}
//	}
//	
//	@Test
//	public void getFolder404Exception(){
//		List<SharepointRessource> folderList;
//		
//		try{
//			folderList = ServiceConnector.getFolder(new UserCredentials("andre.krause", "Abitur200922"), "/projects/aquariu");
//			fail("Exception not thrown");
//		}
//		catch(IOException e){
//			SardineException e1 = (SardineException) e;
//			assertTrue(e1.getStatusCode() == 404);
//		}
//	}
//	
//	@Test
//	public void getFolder401Exception(){
//		List<SharepointRessource> folderList;
//		
//		try{
//			folderList = ServiceConnector.getFolder(new UserCredentials("andre.kraus", "Abitur200922"), "/projects/aquarium");
//			fail("Exception not thrown");
//		}
//		catch(IOException e){
//			SardineException e1 = (SardineException) e;
//			assertTrue(e1.getStatusCode() == 401);
//		}
//	}
//}
