package service.solr;
//package solr;
//
//import java.io.File;
//
//import org.apache.solr.client.solrj.SolrServer;
//import org.apache.solr.client.solrj.impl.HttpSolrServer;
//import org.apache.solr.client.solrj.request.UpdateRequest;
//import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
//import org.apache.solr.common.SolrInputDocument;
//
//import common.Constants;
//import common.SharepointRessource;
//
//public class IndexFiles{
//
//	private static SolrServer solr = new HttpSolrServer(Constants.SOLR_SERVER_ADRESSE);
//	
//	public static boolean indexDocs(SharepointRessource r, String projectName) {
//		File file = new File(r.getArchiv_path());
//		
//		try {
//			UpdateRequest req = new UpdateRequest("/update");
//			
//			String parts[] = file.getName().split("\\.");
//			String type = "text";
//			if (parts.length > 1) {
//				type = parts[1];
//			}
//			
//			String extension = parts[parts.length-1];
//			if(extension.equals("txt") || extension.equals("pdf") || extension.equals("doc") || extension.equals("docx") || extension.equals("xls") || extension.equals("xlsx") || extension.equals("msg") || extension.equals("ppt")||extension.equals("pptx") ){
//				System.out.println("adding " + file);
//				
//				SolrInputDocument document = new SolrInputDocument();
//				
//				MetaContent metaContent = ParseFile.parse(file, type);
//				
//				document.addField("file_path", Constants.sharepointURL + r.getPath());
//				document.addField("name", metaContent.getFileName());
//				document.addField("content_type", metaContent.getContentType());
//				document.addField("file_extension", extension);
//				document.addField("author", r.getAuthor());
//				document.addField("last_modified", r.getLast_modified());
//				document.addField("content", metaContent.getContent());
//				document.addField("project", projectName);
//				
//				req.add(document);
//				
//				req.setAction(ACTION.COMMIT, true, true);
//				
//				req.process(solr);
//				
//				return true;
//			}
//			else
//				return false;
//		} catch (Exception fnfe) {
//			fnfe.printStackTrace();
//			
//			return false;
//		}
//	}
//}
