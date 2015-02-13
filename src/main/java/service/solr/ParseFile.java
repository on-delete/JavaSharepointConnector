package service.solr;

import java.io.File;
import java.io.InputStream;

import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

public class ParseFile {

	public static MetaContent parse(File file, String type){
		
		try{
			InputStream input;
			
			input = TikaInputStream.get(file);
			
			ContentHandler textHandler = new BodyContentHandler(-1);
			Metadata metadata = new Metadata();
			
			AutoDetectParser parser = new AutoDetectParser();
			parser.parse(input, textHandler, metadata, new ParseContext());
			
			input.close();
			
			MetaContent metaContent = new MetaContent();

			if(metadata.get("Content-Type")==null){
				metaContent.setContentType(type);
			}
			else{
				metaContent.setContentType(metadata.get("Content-Type"));
			}
			
			metaContent.setContent(textHandler.toString());
			metaContent.setFileName(file.getName());
			
			return metaContent;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
